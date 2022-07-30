package main.java.org.acme.controller;

import main.java.org.acme.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;

@ApplicationScoped
@Transactional
public class UserController {

    public void getUser(User pUser) {
        User user = User.find("user", pUser).firstResult();

        if (user == null) {
            throw new BadRequestException("Usuário não foi localizado.");
        }

    }

    public void addUser(User pUser) {
        User user = User.find("user", pUser).firstResult();

        if (user != null) {
            throw new BadRequestException("Usuário já cadastrado!");
        }
        user = new User();
        user.email = pUser.email;
        user.nome = pUser.nome;
        user.login = pUser.login;
        user.password = pUser.password;
        user.isAtivo = true;
        user.roleUser = User.NORMAL;
        user.persist();
    }

    public void updateUser(User pUser) {
        User user = User.find("user", pUser).firstResult();

        if (user == pUser) {
            user = new User();
            user.email = pUser.email;
            user.nome = pUser.nome;
            user.login = pUser.login;
            user.password = pUser.password;
            user.isAtivo = true;
            user.persist();
        }
    }

    public void deleteUser(User pUser) {
        User user = User.find("user", pUser).firstResult();
        if (user == pUser)
            user.delete();
        user.persist();
    }

}

