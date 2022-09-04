//package desafio.zoo.controller;
//
//import desafio.zoo.model.Usuario;
//import desafio.zoo.utils.AuthToken;
//import io.smallrye.jwt.auth.principal.JWTParser;
//import io.smallrye.jwt.auth.principal.ParseException;
//import desafio.zoo.model.Auth;
//import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.core.SecurityContext;
//import java.util.Date;
//
//
//@ApplicationScoped
//public class AuthController {
//
//    @Inject
//    AuthToken token;
//
//    @Inject
//    JWTParser parser;
//
//    public Auth login(Auth data) {
//        Usuario usuario = Usuario.find("login", data.login).firstResult();
//
//        if (usuario == null) {
//            throw new BadRequestException("Login ou senha incorretas");
//        }
//
//        boolean authenticated = BCrypt.checkpw(data.password, usuario.password);
//
//        if (!authenticated) {
//            throw new BadRequestException("Login ou senha incorretas");
//        }
//
//        System.out.print("Iniciando login com usuário: " + usuario.login + "\n"); // todo log
//        String accessToken = token.GenerateAccessToken(usuario);
//        String refreshToken = token.GenerateRefreshToken(usuario);
//
//        Long ACTOKEN = 0L;
//        Long RFTOKEN = 0L;
//
//        try {
//            ACTOKEN = parser.parse(accessToken).getClaim("exp");
//            RFTOKEN = parser.parse(refreshToken).getClaim("exp");
//        } catch (ParseException e) {
//            throw new BadRequestException("Credenciais incorretas!");
//        }
//
//        Auth auth = new Auth();
//        auth.usuario = usuario;
//        auth.accessToken = accessToken;
//        auth.refreshToken = refreshToken;
//        auth.expireDateAccessToken = new Date(ACTOKEN * 1000); // milisec -> sec -> Date
//        auth.expireDateRefreshToken = new Date(RFTOKEN * 1000); // milisec -> sec -> Date
//        return auth;
//    }
//
//    public Auth refreshToken(Auth data, SecurityContext context) {
//        Boolean authenticated = false;
//        Usuario usuario = null;
//        Date expireDate = null;
//        try {
//            String login = parser.parse(data.refreshToken).getClaim("upn");
//            long expireDateOldToken = parser.parse(data.refreshToken).getClaim("exp");
//
//            usuario = Usuario.find("login", login).firstResult();
//            expireDate = new Date(expireDateOldToken * 1000); // milsec to sec
//
//            if (expireDate.after(new Date()) && usuario != null) {
//                authenticated = true;
//            } else {
//                throw new BadRequestException("Credenciais incorretas!");
//            }
//        } catch (ParseException error) {
//            System.out.print(error.getMessage()); // todo logger
//        }
//
//        if (authenticated) {
//            String accessToken = token.GenerateAccessToken(usuario);
//            String refreshToken = token.GenerateRefreshToken(usuario);
//            System.out.print("Refresh token solicitado pelo usuário: " + usuario.login + "\n"); // todo logger
//            Long ACTOKEN = 0L;
//            Long RFTOKEN = 0L;
//            try {
//                ACTOKEN = parser.parse(accessToken).getClaim("exp");
//                RFTOKEN = parser.parse(refreshToken).getClaim("exp");
//            } catch (ParseException e) {
//                throw new BadRequestException("Credenciais incorretas!");
//            }
//            Auth auth = new Auth();
//            auth.usuario = usuario;
//            auth.accessToken = accessToken;
//            auth.expireDateAccessToken = new Date(ACTOKEN * 1000); // milisec -> sec -> Date
//            auth.refreshToken = refreshToken;
//            auth.expireDateRefreshToken = new Date(RFTOKEN * 1000); // milisec -> sec -> Date
//            return auth;
//        } else {
//            throw new BadRequestException("Credenciais incorretas!");
//        }
//    }
//}