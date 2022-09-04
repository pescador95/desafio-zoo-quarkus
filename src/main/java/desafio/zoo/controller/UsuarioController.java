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

    public Usuario usuario = new Usuario();
    public Usuario usuarioReturn = new Usuario(); //TODO var criada para retornar obj user pelo método get sem trazer a senha. Estudar para implementar um @JsonIgnoreProperty.
    public List<Usuario> usuarioList = new ArrayList<>();
    public List<Usuario> usuarioListReturn = new ArrayList<>();

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

    public List<Usuario> getUserListAtivos() {

        usuarioList = Usuario.list("isAtivo = true ORDER BY id DESC");

        if ((!usuarioList.isEmpty())) {

            for (int i = 0; i < usuarioList.size(); i++) {
                usuarioReturn = new Usuario();
                usuarioReturn.id = usuarioList.get(i).id;
                usuarioReturn.email = usuarioList.get(i).email;
                usuarioReturn.nome = usuarioList.get(i).nome;
                usuarioReturn.login = usuarioList.get(i).login;
                usuarioReturn.isAtivo = usuarioList.get(i).isAtivo;
                usuarioReturn.dataAcao = usuarioList.get(i).dataAcao;
                usuarioReturn.usuarioAcao = usuarioList.get(i).usuarioAcao;
                usuarioReturn.roleUsuario = usuarioList.get(i).roleUsuario;

                usuarioListReturn.add(usuarioReturn);

            }
        } else {
            throw new BadRequestException("Usuários não localizados ou inativos.");
        }
        return usuarioListReturn;

    }

    public List<Usuario> getUserListInativos() {

        usuarioList = Usuario.list("isAtivo = false ORDER BY id DESC");

        if (usuarioList.isEmpty()) {
            throw new BadRequestException("Usuários inativos não localizados.");
        }
        return usuarioListReturn;
    }

    public void addUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("login = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.login).firstResult();

        if ((usuario == null) || !usuario.isAtivo) {
            usuario = new Usuario();
            usuario.email = pUsuario.email;
            usuario.nome = pUsuario.nome;
            usuario.login = pUsuario.login;
            usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
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
                usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            }
            if (!Objects.equals(usuario.roleUsuario, pUsuario.roleUsuario)) {
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
            usuario.usuarioAcao = "usuario que deletou";
            usuario.systemDateDeleted = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o Usuário.");
        }
    }

}