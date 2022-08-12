package main.java.desafiozoo.controller;

import main.java.desafiozoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.time.ZonedDateTime;

@ApplicationScoped
@Transactional
public class UsuarioController {
    public String mensagem;
    public Usuario usuario;
    public Usuario usuarioReturn; //TODO var criada para retornar obj user pelo método get sem trazer a senha. Estudar para implementar um @JsonIgnoreProperty.

    public Usuario getUser(@NotNull Usuario pUsuario) {
       usuario = Usuario.find("login", pUsuario.login).firstResult();

        if (!(usuario == null)) {
            usuarioReturn = new Usuario();
            usuarioReturn.id = usuario.id;
            usuarioReturn.email = usuario.email;
            usuarioReturn.nome = usuario.nome;
            usuarioReturn.login = usuario.login;
            usuarioReturn.isAtivo = usuario.isAtivo;
            usuarioReturn.roleUsuario = usuario.roleUsuario;
        } else {
            mensagem = ("Usuário não foi localizado.");
            throw new BadRequestException("Usuário não foi localizado.");

        }
    return usuarioReturn;
    }

    public void addUser(@NotNull Usuario pUsuario) {
        usuario = Usuario.find("login", pUsuario.login).firstResult();

        if (usuario == null) {
            usuario = new Usuario();
            usuario.email = pUsuario.email;
            usuario.nome = pUsuario.nome;
            usuario.login = pUsuario.login;
            usuario.password = pUsuario.password;
            usuario.isAtivo = true;
            usuario.roleUsuario = pUsuario.roleUsuario;
            usuario.usuarioAcao = "";
            usuario.dataAcao = ZonedDateTime.now();
            usuario.persist();
            mensagem = "usuário criado com sucesso!";

        } else {
            mensagem = ("Usuário já cadastrado!");
            throw new BadRequestException("Usuário já cadastrado!");
        }

    }

    public void updateUser(@NotNull Usuario pUsuario) {
        usuario = Usuario.find("login", pUsuario.login).firstResult();

        if (!(usuario == null) && usuario.login.equals(pUsuario.login)) {
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
            usuario.dataAcao = ZonedDateTime.now();
            usuario.persist();
            mensagem = "usuário atualizado com sucesso!";
        } else {
            mensagem = ("Não foi possível atualizar o Usuário.");
            throw new BadRequestException("Não foi possível atualizar o Usuário.");

        }
    }

    public void deleteUser(@NotNull Usuario pUsuario) {
        usuario = Usuario.find("login", pUsuario.login).firstResult();


            if (!(usuario == null) && usuario.login.equals(pUsuario.login)) {
                usuario.isAtivo = false;
                usuario.usuarioAcao = "usuario";
                usuario.dataAcao = ZonedDateTime.now();
                usuario.persist();
                mensagem = "Usuário deletado com sucesso!";
            } else {
                mensagem = ("Não foi possível deletar o Usuário.");
                throw new BadRequestException("Não foi possível deletar o Usuário.");
            }
    }
}

