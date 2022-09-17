package desafio.zoo.controller;

import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UsuarioController {
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarioList = new ArrayList<>();

    public Usuario getUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("id = ?1 ORDER BY id DESC", pUsuario.id).firstResult();

        if (((usuario == null)) && (!usuario.isAtivo)) {
            throw new BadRequestException("Usuário não localizado ou inativo.");
        }
        return usuario;

    }

    public List<Usuario> getUserListAtivos() {

        usuarioList = Usuario.list("isAtivo = true ORDER BY id DESC");

        if (usuarioList.isEmpty()) {
            throw new BadRequestException("Usuários não localizados ou inativos.");
        }
        return usuarioList;

    }

    public List<Usuario> getUserListInativos() {

        usuarioList = Usuario.list("isAtivo = false ORDER BY id DESC");

        if (usuarioList.isEmpty()) {
            throw new BadRequestException("Usuários inativos não localizados.");
        }
        return usuarioList;
    }

    public void addUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("email = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.email).firstResult();

        if ((usuario == null) || !usuario.isAtivo) {
            usuario = new Usuario();
            usuario.email = pUsuario.email;
            usuario.nome = pUsuario.nome;
            usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            usuario.isAtivo = true;
            usuario.roleUsuario = pUsuario.roleUsuario;
            usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);
            usuario.dataAcao = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Usuário já cadastrado!");
        }
    }


    public void updateUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();

        if (!(usuario == null)) {
            if (!usuario.email.equals(pUsuario.email)) {
                usuario.email = pUsuario.email;
            }
            if (!usuario.nome.equals(pUsuario.nome)) {
                usuario.nome = pUsuario.nome;
            }
            if (!usuario.password.equals(pUsuario.password)) {
                usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            }
            if (!Objects.equals(usuario.roleUsuario, pUsuario.roleUsuario)) {
                usuario.roleUsuario = pUsuario.roleUsuario;
            }
            usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);

            usuario.dataAcao = new Date();

            usuario.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Usuário.");

        }
    }

    public void deleteUser(@NotNull List<Usuario> usuarioList) {

        usuarioList.forEach((pUsuario) -> {
            Usuario usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();

            if (usuario != null) {
                usuario.isAtivo = Boolean.FALSE;
                usuario.dataAcao = new Date();
                usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);
                usuario.systemDateDeleted = new Date();
                usuario.persist();
            } else {
                throw new BadRequestException("Usuários não localizados ou inativos.");
            }
        });
    }

}