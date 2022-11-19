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
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.Date;

@ApplicationScoped
@Transactional
public class RecoverPasswordController {
    @Inject
    Mailer mailer;

    Responses responses;

    public void sendEmail(String email) {

        Usuario usuario = Usuario.find("email = ?1 and isAtivo = true", email).firstResult();
        String senha = RandomString.make(12);
        System.out.println(senha);
        usuario.password = BcryptUtil.bcryptHash(senha);
        usuario.persist();
        String nome = usuario.nome;

        if (usuario != null) {
            mailer.send(Mail.withText(email, "Desafio Zoo - Recuperação de Senha", "Caro " + nome + ",\n"
                    + "segue a nova senha para realização do acesso ao sistema do Zoo: " + senha));
        } else {
            throw new NotFoundException("Usuários não cadastrado ou inativo");
        }
    }

    public void updatePassword(String email, String password) {

        Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        if (password != null && !password.equals(usuarioAuth.password)) {
            usuarioAuth.password = BcryptUtil.bcryptHash(password);
            usuarioAuth.usuarioAcao = usuarioAuth.nome;
            usuarioAuth.dataAcao = new Date();
            usuarioAuth.persist();
        } else {
            responses.messages.add("Não foi possível atualizar a senha.");
        }
    }
}