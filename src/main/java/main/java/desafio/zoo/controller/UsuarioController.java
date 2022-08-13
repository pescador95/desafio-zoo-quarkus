package main.java.desafio.zoo.controller;

import main.java.desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Date;

@ApplicationScoped
@Transactional
public class UsuarioController {

    public Usuario usuario;
    public Usuario usuarioReturn; //TODO var criada para retornar obj user pelo método get sem trazer a senha. Estudar para implementar um @JsonIgnoreProperty.


    public Usuario getUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("login = ?1 ORDER BY id DESC", pUsuario.login).firstResult();

        if ((!(usuario == null)) && (usuario.isAtivo)) {
            usuarioReturn = new Usuario();
            usuarioReturn.id = usuario.id;
            usuarioReturn.email = usuario.email;
            usuarioReturn.nome = usuario.nome;
            usuarioReturn.login = usuario.login;
            usuarioReturn.isAtivo = usuario.isAtivo;
            usuarioReturn.dataAcao = usuario.dataAcao;
            usuarioReturn.usuarioAcao = usuario.usuarioAcao;
            usuarioReturn.roleUsuario = usuario.roleUsuario;

        } else {
            throw new BadRequestException("Usuário não localizado ou inativo.");
        }
        return usuarioReturn;

    }


    public void addUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("login = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.login).firstResult();

        if ((usuario == null) || !usuario.isAtivo) {
            usuario = new Usuario();
            usuario.email = pUsuario.email;
            usuario.nome = pUsuario.nome;
            usuario.login = pUsuario.login;
            usuario.password = pUsuario.password;
            usuario.isAtivo = true;
            usuario.roleUsuario = pUsuario.roleUsuario;
            usuario.usuarioAcao = "";
            usuario.dataAcao = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Usuário já cadastrado!");
        }
    }


    public void updateUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("login = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.login).firstResult();

        if (!(usuario == null) && usuario.login.equals(pUsuario.login) && usuario.isAtivo) {
            if (!usuario.email.equals(pUsuario.email)) {
                usuario.email = pUsuario.email;
            }
            if (!usuario.nome.equals(pUsuario.nome)) {
                usuario.nome = pUsuario.nome;
            }
            if (!usuario.password.equals(pUsuario.password)) {
                usuario.password = pUsuario.password;
            }
            if (usuario.roleUsuario != pUsuario.roleUsuario) {
                usuario.roleUsuario = pUsuario.roleUsuario;
            }
            usuario.usuarioAcao = "";
            usuario.dataAcao = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Usuário.");

        }
    }

    public void deleteUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("login = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.login).firstResult();

        if (!(usuario == null) && usuario.login.equals(pUsuario.login) && (usuario.isAtivo)) {

            usuario.isAtivo = false;
            usuario.usuarioAcao = "usuario";
            usuario.systemDateDeleted = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o Usuário.");
        }
    }
}