package desafio.zoo.controller;

import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UsuarioController<pId> {
    private Usuario usuario = new Usuario();

    public void addUser(@NotNull Usuario pUsuario, String email) {

        Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        usuario = Usuario.find("email = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.email).firstResult();

        if (usuario == null) {
            usuario = new Usuario();

            if (pUsuario.email != null) {
                usuario.email = pUsuario.email;
            } else {
                throw new BadRequestException("Por favor, preencha o email do Usuário corretamente!");
            }
            if (pUsuario.nome != null) {
                usuario.nome = pUsuario.nome;
            } else {
                throw new BadRequestException("Por favor, preencha o nome do Usuário corretamente!");
            }
            if (pUsuario.password != null) {
                usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            } else {
                throw new BadRequestException("Por favor, preencha a senha do Usuário corretamente!");
            }

            if (pUsuario.roleUsuario != null) {
                usuario.roleUsuario = pUsuario.roleUsuario;
            } else {
                throw new BadRequestException("Por favor, preencha a permissão do Usuário corretamente!");
            }
            usuario.usuario = usuarioAuth.nome;
            usuario.usuarioAcao = usuarioAuth.nome;
            usuario.isAtivo = Boolean.TRUE;
            usuario.dataAcao = new Date();
            usuario.persist();

        } else {
            throw new BadRequestException("Usuário já cadastrado!");
        }
    }

    public void updateUser(@NotNull Usuario pUsuario, String email) {

        Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();

        if (usuario != null) {
            if (pUsuario.email == null && pUsuario.nome == null && pUsuario.password == null
                    && pUsuario.roleUsuario == null) {
                throw new BadRequestException("Informe os dados para atualizar o Usuário.");
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
                usuario.usuarioAcao = usuarioAuth.nome;
                usuario.dataAcao = new Date();
                usuario.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Usuário.");
        }
    }

    public void deleteUser(@NotNull List<Long> pListIdusuario, String email) {

        Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        pListIdusuario.forEach((pUsuario) -> {
            Usuario usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario).firstResult();

            if (usuario != null) {
                usuario.usuarioAcao = usuarioAuth.nome;
                usuario.isAtivo = Boolean.FALSE;
                usuario.dataAcao = new Date();
                usuario.systemDateDeleted = new Date();
                usuario.persist();
            } else {
                if (pListIdusuario.size() <= 1) {
                    throw new NotFoundException("Usuário não localizado ou já excluído.");
                } else {
                    throw new NotFoundException("Usuários não localizados ou já excluídos.");
                }
            }
        });
    }

    public void reactivateUser(@NotNull List<Long> pListIdusuario, String email) {

        Usuario usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        pListIdusuario.forEach((pUsuario) -> {
            Usuario usuario = Usuario.find("id = ?1 and isAtivo = false ORDER BY id DESC", pUsuario).firstResult();

            if (usuario != null) {
              usuario.usuarioAcao = usuarioAuth.nome;
                usuario.isAtivo = Boolean.TRUE;
                usuario.dataAcao = new Date();
                usuario.systemDateDeleted = null;
                usuario.persist();
            } else {
                if (pListIdusuario.size() <= 1) {
                    throw new NotFoundException("Usuário inativo não localizado ou já reativado.");
                } else {
                    throw new NotFoundException("Usuários inativos não localizados ou já reativados.");
                }
            }
        });
    }
}
