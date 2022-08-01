package main.java.org.acme.controller;

import main.java.org.acme.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UserController {

    public void getUser(User pUser) {
        User user = User.find("user", pUser.login).firstResult();
        String mensagem;

        if (user == null) {
            mensagem = ("Usuário não foi localizado.");
            throw new BadRequestException("Usuário não foi localizado.");

        }

    }

    public void addUser(User pUser) {
        User user = User.find("user", pUser.login).firstResult();
        String mensagem;

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
        User user = User.find("user", pUser.login).firstResult();
        String mensagem;

        if (user == pUser) {
            user = new User();
            user.email = pUser.email;
            user.nome = pUser.nome;
            user.login = pUser.login;
            user.password = pUser.password;
            user.isAtivo = true;
            user.persist();
            mensagem = "usuário atualizado com sucesso!";
        } else {
            mensagem = ("Não foi possível atualizar o Usuário.");
            throw new BadRequestException("Não foi possível atualizar o Usuário.");

        }
    }

    public void deleteUser(User pUser) {
        User user = User.find("user", pUser.login).firstResult();
        String mensagem;

        if (!Objects.isNull(user)) {
            if (Objects.equals(user.login, pUser.login)) {
                user.delete();
                user.persist();
                mensagem = "Usuário deletado com sucesso!";
            } else {
                mensagem = ("Não foi possível atualizar o Usuário.");
                throw new BadRequestException("Não foi possível atualizar o Usuário.");
            }
        } else {
            mensagem = ("Não foi possível atualizar o Usuário.");
            throw new BadRequestException("Não foi possível atualizar o Usuário.");
        }
    }
}
;
