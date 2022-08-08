package main.java.org.acme.controller;

import main.java.org.acme.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UserController {
    public String mensagem;
    public User user;
    public User userReturn; //TODO var criada para retornar obj user pelo método get sem trazer a senha. Estudar para implementar um @JsonIgnoreProperty.

    public User getUser(User pUser) {
       user = User.find("login", pUser.login).firstResult();

        userReturn = new User();
            userReturn.id = user.id;
            userReturn.email = user.email;
            userReturn.nome = user.nome;
            userReturn.login = user.login;
            userReturn.isAtivo = user.isAtivo;
            userReturn.roleUser = user.roleUser;
        if (user == null) {
            mensagem = ("Usuário não foi localizado.");
            throw new BadRequestException("Usuário não foi localizado.");

        }
    return userReturn;
    }

    public void addUser(User pUser) {
        user = User.find("login", pUser.login).firstResult();

        if (user == null) {
            user = new User();
            user.email = pUser.email;
            user.nome = pUser.nome;
            user.login = pUser.login;
            user.password = pUser.password;
            user.isAtivo = true;
            user.roleUser = User.NORMAL;
            user.persist();
            mensagem = "usuário criado com sucesso!";

        } else {
            mensagem = ("Usuário já cadastrado!");
            throw new BadRequestException("Usuário já cadastrado!");
        }

    }

    public void updateUser(User pUser) {
        user = User.find("login", pUser.login).firstResult();

        if (user.login.equals(pUser.login)) {
            if (!user.email.equals(pUser.email)) {
                user.email = pUser.email;
            }
            if (!user.nome.equals(pUser.nome)) {
                user.nome = pUser.nome;
            }
            if (!user.password.equals(pUser.password)) {
                user.password = pUser.password;
            }
            user.persist();
            mensagem = "usuário atualizado com sucesso!";
        } else {
            mensagem = ("Não foi possível atualizar o Usuário.");
            throw new BadRequestException("Não foi possível atualizar o Usuário.");

        }
    }

    public void deleteUser(User pUser) {
        user = User.find("login", pUser.login).firstResult();

        if (!Objects.isNull(user)) {
            if (user.login.equals(pUser.login)) {
                user.delete();
                mensagem = "Usuário deletado com sucesso!";
            } else {
                mensagem = ("Não foi possível deletar o Usuário.");
                throw new BadRequestException("Não foi possível deletar o Usuário.");
            }
        } else {
            mensagem = ("Não foi possível deletar o Usuário.");
            throw new BadRequestException("Não foi possível localizar o Usuário para deletar.");
        }
    }
}
;
