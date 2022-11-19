package desafio.zoo.controller;

import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
@Transactional
public class UsuarioController<pId> {
    private Usuario usuario = new Usuario();

    Responses responses;

    Usuario usuarioAuth;

    public Response addUser(@NotNull Usuario pUsuario, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        usuario = Usuario.find("email = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.email).firstResult();

        if (usuario == null) {
            usuario = new Usuario();

            if (pUsuario.email != null) {
                usuario.email = pUsuario.email;
            } else {
                responses.messages.add("Por favor, preencha o email do Usuário corretamente!");
            }
            if (pUsuario.nome != null) {
                usuario.nome = pUsuario.nome;
            } else {
                responses.messages.add("Por favor, preencha o nome do Usuário corretamente!");
            }
            if (pUsuario.password != null) {
                usuario.password = BcryptUtil.bcryptHash(pUsuario.password);
            } else {
                responses.messages.add("Por favor, preencha a senha do Usuário corretamente!");
            }

            if (pUsuario.roleUsuario != null) {
                usuario.roleUsuario = pUsuario.roleUsuario;
            } else {
                responses.messages.add("Por favor, preencha a permissão do Usuário corretamente!");
            }
            if (responses.messages.size() < 1) {
                usuario.usuario = usuarioAuth.nome;
                usuario.usuarioAcao = usuarioAuth.nome;
                usuario.isAtivo = Boolean.TRUE;
                usuario.dataAcao = new Date();
                usuario.persist();

                responses.status = 201;
                responses.data = usuario;
                responses.messages.add("Usuário Cadastrado com sucesso!");
            } else {
                return Response.ok(responses).status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 500;
            responses.data = usuario;
            responses.messages.add("Usuário já cadastrado!");
            return Response.ok(responses).status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    public Response updateUser(@NotNull Usuario pUsuario, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();


        try {
            usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pUsuario.email == null && pUsuario.nome == null && pUsuario.password == null
                    && pUsuario.roleUsuario == null) {
                responses.messages.add("Informe os dados para atualizar o Usuário.");
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

                responses.status = 200;
                responses.data = usuario;
                responses.messages.add("Usuário atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = usuario;
            responses.messages.add("Não foi possível atualizar o Usuário.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteUser(@NotNull List<Long> pListIdusuario, String email) {

        Integer countList = pListIdusuario.size();
        List<Usuario> usuarioList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdusuario.forEach((pUsuario) -> {
                Usuario usuario = Usuario.find("id = ?1 and isAtivo = true ORDER BY id DESC", pUsuario).firstResult();

                usuario.usuarioAcao = usuarioAuth.nome;
                usuario.isAtivo = Boolean.FALSE;
                usuario.dataAcao = new Date();
                usuario.systemDateDeleted = new Date();
                usuario.persist();
                usuarioList.add(usuario);
            });
            if (pListIdusuario.size() <= 1) {
                responses.status = 200;
                responses.data = usuario;
                responses.messages.add("Usuário reativado com sucesso!");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(usuarioList);
                responses.messages.add(countList + " Usuários reativados com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdusuario.size() <= 1) {
                responses.status = 500;
                responses.data = usuario;
                responses.messages.add("Usuário não localizado ou já excluído.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(usuarioList);
                responses.messages.add("Usuários não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateUser(@NotNull List<Long> pListIdusuario, String email) {

        Integer countList = pListIdusuario.size();
        List<Usuario> usuarioList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdusuario.forEach((pUsuario) -> {
                Usuario usuario = Usuario.find("id = ?1 and isAtivo = false ORDER BY id DESC", pUsuario).firstResult();

                usuario.usuarioAcao = usuarioAuth.nome;
                usuario.isAtivo = Boolean.TRUE;
                usuario.dataAcao = new Date();
                usuario.systemDateDeleted = new Date();
                usuario.persist();
                usuarioList.add(usuario);
            });
            if (pListIdusuario.size() <= 1) {
                responses.status = 200;
                responses.data = usuario;
                responses.messages.add("Usuário reativado com sucesso!");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(usuarioList);
                responses.messages.add(countList + " Usuários reativados com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdusuario.size() <= 1) {
                responses.status = 500;
                responses.data = usuario;
                responses.messages.add("Usuário não localizado ou já reativado.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(usuarioList);
                responses.messages.add("Usuários não localizados ou já reativados.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
