package desafio.zoo.controller;

import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
@Transactional
public class RecuperaSenhaController {
    
    //private Usuario usuario = new Usuario();
    @Inject
    Mailer mailer;

    public String enviaEmail(String email) {
        System.out.println("enviaEmail OK");
        Usuario usuario = Usuario.find("email = ?1 and isAtivo = true", email).firstResult();
        System.out.println("Verifica usuario OK");
        System.out.println("Email: " + email);
        System.out.println(mailer);

        if (usuario != null) {
            /*Faz o envio do email*/
            System.out.println("Verifica se e nulo OK");
            mailer.send(Mail.withText(email, "Quarkus mailer test titulo", "Quarkus mailer test corpo"));
            System.out.println("Envia o email OK");
            return "Email enviado";
            
        } else {
            throw new NotFoundException("Usuários não cadastrado ou inativo");
        }
    }
}