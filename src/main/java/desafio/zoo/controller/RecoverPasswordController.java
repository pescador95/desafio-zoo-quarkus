package desafio.zoo.controller;

import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import net.bytebuddy.utility.RandomString;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

@ApplicationScoped
@Transactional
public class RecoverPasswordController {
    @Inject
    Mailer mailer;

    Responses responses;

    public Response sendEmail(String email) {
        responses = new Responses();
        responses.messages = new ArrayList<>();

        Usuario usuario = Usuario.find("email = ?1 and isAtivo = true", email).firstResult();
        if (usuario.isAtivo) {
            String senha = RandomString.make(12);
            System.out.println(senha);
            usuario.password = BcryptUtil.bcryptHash(senha);
            usuario.persist();
            String nome = usuario.nome;
            mailer.send(Mail.withText(email, "Desafio Zoo - Recuperação de Senha", "Caro " + nome + ",\n"
                    + "segue a nova senha para realização do acesso ao sistema do Zoo: " + senha));
            responses.status = 200;
            responses.data = usuario;
            responses.messages.add("Enviado uma nova senha para o email informado.");
        } else {
            responses = new Responses();
            responses.status = 400;
            responses.messages.add("Não foi possível localizar um cadastro com o email informado.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(responses).status(Response.Status.ACCEPTED).build();
    }

    public Response updatePassword(String email, String password) {
        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {
            Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            usuarioAuth.password = BcryptUtil.bcryptHash(password);
            usuarioAuth.usuarioAcao = usuarioAuth.nome;
            usuarioAuth.dataAcao = new Date();
            usuarioAuth.persist();

            responses.status = 200;
            responses.data = usuarioAuth;
            responses.messages.add("Senha atualizada com sucesso.");
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar a senha.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}