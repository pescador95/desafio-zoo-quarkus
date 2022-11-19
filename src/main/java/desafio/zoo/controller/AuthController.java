package desafio.zoo.controller;

import desafio.zoo.model.Auth;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import desafio.zoo.utils.AuthToken;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;

@ApplicationScoped
public class AuthController {

    @Inject
    AuthToken token;

    @Inject
    JWTParser parser;
    Responses responses;

    public Auth login(Auth data) {
        Usuario usuario = Usuario.find("email", data.email).firstResult();

        if (usuario == null) {
            responses.messages.add("email ou senha incorretas");
        }

        boolean authenticated = BCrypt.checkpw(data.password, usuario.password);

        if (!authenticated) {
            responses.messages.add("email ou senha incorretas");
        }

        System.out.print("\n" + "Iniciando login com usuário: " + usuario.email + "..." + "\n" + "\n" + "Bem vindo, "
                + usuario.nome + "!");
        String accessToken = token.GenerateAccessToken(usuario);
        String refreshToken = token.GenerateRefreshToken(usuario);

        Long ACTOKEN = 0L;
        Long RFTOKEN = 0L;

        try {
            ACTOKEN = parser.parse(accessToken).getClaim("exp");
            RFTOKEN = parser.parse(refreshToken).getClaim("exp");
        } catch (ParseException e) {
            responses.messages.add("Credenciais incorretas!");
        }

        Auth auth = new Auth();
        auth.nomeUsuario = usuario.nome;
        auth.email = usuario.email;
        auth.password = BcryptUtil.bcryptHash(usuario.password);
        auth.roleUsuario = usuario.roleUsuario;
        auth.accessToken = accessToken;
        auth.refreshToken = refreshToken;
        auth.usuario = usuario;
        auth.expireDateAccessToken = new Date(ACTOKEN * 1000); // milisec -> sec -> Date
        auth.expireDateRefreshToken = new Date(RFTOKEN * 1000); // milisec -> sec -> Date
        return auth;
    }

    public Auth refreshToken(Auth data) {
        Boolean authenticated = false;
        Usuario usuario = null;
        Date expireDate = null;
        try {
            String email = parser.parse(data.refreshToken).getClaim("upn");
            long expireDateOldToken = parser.parse(data.refreshToken).getClaim("exp");

            usuario = Usuario.find("email", email).firstResult();
            expireDate = new Date(expireDateOldToken * 1000); // milsec to sec

            if (expireDate.after(new Date()) && usuario != null) {
                authenticated = true;
            } else {
                responses.messages.add("Credenciais incorretas!");
            }
        } catch (ParseException error) {
            System.out.print(error.getMessage());
        }

        if (authenticated) {
            String accessToken = token.GenerateAccessToken(usuario);
            String refreshToken = token.GenerateRefreshToken(usuario);
            System.out.print("Refresh token solicitado pelo usuário: " + usuario.email + "\n");
            Long ACTOKEN = 0L;
            Long RFTOKEN = 0L;
            try {
                ACTOKEN = parser.parse(accessToken).getClaim("exp");
                RFTOKEN = parser.parse(refreshToken).getClaim("exp");
            } catch (ParseException e) {
                responses.messages.add("Credenciais incorretas!");
            }
            Auth auth = new Auth();
            auth.nomeUsuario = usuario.nome;
            auth.email = usuario.email;
            auth.roleUsuario = usuario.roleUsuario;
            auth.usuario = usuario;
            auth.accessToken = accessToken;
            auth.expireDateAccessToken = new Date(ACTOKEN * 1000); // milisec -> sec -> Date
            auth.refreshToken = refreshToken;
            auth.expireDateRefreshToken = new Date(RFTOKEN * 1000); // milisec -> sec -> Date
            return auth;
        } else {
            responses.messages.add("Credenciais incorretas");
        }
        return data;
    }
}
