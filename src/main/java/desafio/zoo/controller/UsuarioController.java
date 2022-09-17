package desafio.zoo.controller;

import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
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
            throw new NotFoundException("Usuário não localizado ou inativo.");//TODO organizar mensagem
        }
        return usuario;

    }

    public List<Usuario> getUserListAtivos() {

        usuarioList = Usuario.list("isAtivo = true ORDER BY id DESC");

        if (usuarioList.isEmpty()) {
            throw new NotFoundException("Usuários não localizados ou inativos.");//TODO organizar mensagem
        }
        return usuarioList;

    }

    public List<Usuario> getUserListInativos() {

        usuarioList = Usuario.list("isAtivo = false ORDER BY id DESC");

        if (usuarioList.isEmpty()) {
            throw new NotFoundException("Usuários inativos não localizados.");//TODO organizar mensagem
        }
        return usuarioList;
    }

    public void addUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("email = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.email).firstResult();

        if (usuario == null) {
            usuario = new Usuario();

            if (pUsuario.email != null) {
                usuario.email = pUsuario.email;
            } else {
                throw new BadRequestException("Por favor, preencha o email do Usuário corretamente!");//TODO organizar mensagem
            }
            if (pUsuario.nome != null) {
                usuario.nome = pUsuario.nome;
            } else {
                throw new BadRequestException("Por favor, preencha o nome do Usuário corretamente!");//TODO organizar mensagem
            }
            if (pUsuario.password != null) {
                usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            } else {
                throw new BadRequestException("Por favor, preencha a senha do Usuário corretamente!");//TODO organizar mensagem
            }

            if (pUsuario.roleUsuario != null) {
                usuario.roleUsuario = pUsuario.roleUsuario;
            } else {
                throw new BadRequestException("Por favor, preencha a permissão do Usuário corretamente!");//TODO organizar mensagem
            }
            usuario.isAtivo = Boolean.TRUE;
            //usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id); //TODO usuario ação para usuário
            usuario.dataAcao = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Usuário já cadastrado!");//TODO organizar mensagem
        }

    }


    public void updateUser(@NotNull Usuario pUsuario) {

        usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();

        if (usuario != null) {
            if (pUsuario.email == null && pUsuario.nome == null && pUsuario.password == null && pUsuario.roleUsuario == null) {
                throw new BadRequestException("Informe os dados para atualizar o Usuário.");//TODO organizar mensagem
            } else {
                if (pUsuario.email != null) {
                    if (!usuario.email.equals(pUsuario.email)) {
                        usuario.email = pUsuario.email;
                    }
                }
                if (pUsuario.nome != null) {
                    if (!usuario.nome.equals(pUsuario.nome)) {
                        usuario.nome = pUsuario.nome;
                    }
                }
                if (pUsuario.password != null) {
                    if (usuario.password != null && !usuario.password.equals(pUsuario.password)) {
                        usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
                    }
                }
                if (pUsuario.roleUsuario != null) {
                    if (!Objects.equals(usuario.roleUsuario, pUsuario.roleUsuario)) {
                        usuario.roleUsuario = pUsuario.roleUsuario;
                    }
                }

                // usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);  //TODO usuario ação para usuário
                usuario.dataAcao = new Date();
                usuario.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Usuário.");//TODO organizar mensagem

        }
    }

    public void deleteUser(@NotNull List<Usuario> usuarioList) {

        usuarioList.forEach((pUsuario) -> {
            Usuario usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();

            if (usuario != null) {
                usuario.isAtivo = Boolean.FALSE;
                usuario.dataAcao = new Date();
                //  usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);  //TODO usuario ação para usuário
                usuario.systemDateDeleted = new Date();
                usuario.persist();
            } else {
                if (usuarioList.size() <= 1) {
                    throw new NotFoundException("Usuário não localizado ou já excluído.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Usuários não localizados ou já excluídos.");//TODO organizar mensagem
                }

            }
        });
    }

    public void reactivateUser(@NotNull List<Usuario> usuarioList) {

        usuarioList.forEach((pUsuario) -> {
            Usuario usuario = Usuario.find("id = ?1 and isAtivo = false ORDER BY id DESC", pUsuario.id).firstResult();

            if (usuario != null) {
                usuario.isAtivo = Boolean.TRUE;
                usuario.dataAcao = new Date();
                //  usuario.usuarioAcao = Usuario.findById(pUsuario.usuarioAcao.id);  //TODO usuario ação para usuário
                usuario.systemDateDeleted = null;
                usuario.persist();
            } else {
                if (usuarioList.size() <= 1) {
                    throw new NotFoundException("Usuário inativo não localizado ou já reativado.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Usuários inativos não localizados ou já reativados.");//TODO organizar mensagem
                }

            }
        });
    }
}


