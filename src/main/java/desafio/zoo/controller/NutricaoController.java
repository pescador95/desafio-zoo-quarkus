package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Nutricao;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional
public class NutricaoController {

    public Nutricao nutricao;
    public Animal animal;
    Responses responses;
    Usuario usuarioAuth;

    public Response addNutricao(@NotNull Nutricao pNutricao, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        nutricao = Nutricao.find("animal = ?1 and isAtivo = true and dataInicio = ?2 ORDER BY id DESC",
                pNutricao.animal, pNutricao.dataInicio).firstResult();
        animal = Animal.find("id = ?1", pNutricao.animal.id).firstResult();

        if (nutricao == null) {
            nutricao = new Nutricao();

            if (pNutricao.descricaoNutricao != null) {
                nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            } else {
                responses.messages.add("Por favor, preencha a descrição da Nutrição corretamente!");
            }

            nutricao.isAtivo = Boolean.TRUE;

            if (pNutricao.dataInicio != null) {
                nutricao.dataInicio = pNutricao.dataInicio;
            } else {
                responses.messages.add("Por favor, preencha a data de Início da Nutrição corretamente!");
            }

            if (pNutricao.dataFim != null) {
                nutricao.dataFim = pNutricao.dataFim;

            } else {
                responses.messages.add("Por favor, preencha a data fim da Nutrição corretamente!");
            }
            if (pNutricao.animal != null) {
                nutricao.animal = Animal.findById(pNutricao.animal.id);
            } else {
                responses.messages.add("Por favor, preencha o Animal da Nutrição corretamente!");
            }
            if (responses.messages.size() < 1) {
                nutricao.isAtivo = Boolean.TRUE;
                nutricao.nomeAnimal = animal.nomeApelido;
                nutricao.usuario = usuarioAuth;
                nutricao.usuarioAcao = usuarioAuth;
                nutricao.usuarioNome = usuarioAuth.nome;
                nutricao.usuarioAcaoNome = usuarioAuth.nome;
                nutricao.dataAcao = new Date();

                nutricao.persist();

                responses.status = 201;
                responses.data = nutricao;
                responses.messages.add("Ficha de nutrição Cadastrada com sucesso!");
            } else {
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 500;
            responses.data = nutricao;
            responses.messages.add("Ficha de nutrição já cadastrada!");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response updateNutricao(@NotNull Nutricao pNutricao, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {

            nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pNutricao.dataInicio == null && pNutricao.dataFim == null && pNutricao.descricaoNutricao == null
                    && pNutricao.animal == null) {
                responses.messages.add("Informe os dados para atualizar a Nutrição.");
            } else {
                if (pNutricao.dataInicio != null && nutricao.dataInicio != null) {
                    if (!nutricao.dataInicio.equals(pNutricao.dataInicio)) {
                        nutricao.dataInicio = pNutricao.dataInicio;
                    }
                }
                if (pNutricao.dataFim != null && nutricao.dataFim != null) {
                    if (!nutricao.dataFim.equals(pNutricao.dataFim)) {
                        nutricao.dataFim = pNutricao.dataFim;
                    }
                }
                if (pNutricao.descricaoNutricao != null && nutricao.descricaoNutricao != null) {
                    if (!nutricao.descricaoNutricao.equals(pNutricao.descricaoNutricao)) {
                        nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
                    }
                }
                if (pNutricao.animal != null && nutricao.animal != null) {
                    if (nutricao.animal != null && !nutricao.animal.equals(pNutricao.animal)) {
                        nutricao.animal = Animal.findById(pNutricao.animal.id);
                    }
                }
                nutricao.nomeAnimal = nutricao.animal.nomeApelido;
                nutricao.usuarioAcao = usuarioAuth;
                nutricao.usuarioAcaoNome = usuarioAuth.nome;
                nutricao.dataAcao = new Date();
                nutricao.persist();

                responses.status = 200;
                responses.data = nutricao;
                responses.messages.add("Ficha de Nutricação atualizada com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = nutricao;
            responses.messages.add("Não foi possível atualizar a Ficha de Nutrição.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteNutricao(List<Long> pListIdnutricao, String email) {

        Integer countList = pListIdnutricao.size();
        List<Nutricao> nutricaoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdnutricao.forEach((pNutricao) -> {
                nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao).firstResult();

                nutricao.isAtivo = Boolean.FALSE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = usuarioAuth;
                nutricao.usuarioAcaoNome = usuarioAuth.nome;
                nutricao.systemDateDeleted = new Date();
                nutricao.persist();
                nutricaoList.add(nutricao);
            });
            if (pListIdnutricao.size() <= 1) {
                responses.status = 200;
                responses.data = animal;
                responses.messages.add("Ficha de Nutricação excluída com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(nutricaoList);
                responses.messages.add(countList + " Fichas de Nutricação excluídas com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdnutricao.size() <= 1) {
                responses.status = 500;
                responses.data = animal;
                responses.messages.add("Ficha de Nutricação não localizada ou já excluída.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(nutricaoList);
                responses.messages.add("Fichas de Nutricação não localizadas ou já excluídas.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateNutricao(List<Long> pListIdnutricao, String email) {

        Integer countList = pListIdnutricao.size();
        List<Nutricao> nutricaoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdnutricao.forEach((pNutricao) -> {
                nutricao = Nutricao.find("id = ?1 and isAtivo = false ORDER BY id DESC", pNutricao).firstResult();

                nutricao.isAtivo = Boolean.TRUE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = usuarioAuth;
                nutricao.usuarioAcaoNome = usuarioAuth.nome;
                nutricao.systemDateDeleted = new Date();
                nutricao.persist();
                nutricaoList.add(nutricao);
            });
            if (pListIdnutricao.size() <= 1) {
                responses.status = 200;
                responses.data = animal;
                responses.messages.add("Ficha de Nutricação excluída com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(nutricaoList);
                responses.messages.add(countList + " Fichas de Nutricação excluídas com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdnutricao.size() <= 1) {
                responses.status = 500;
                responses.data = animal;
                responses.messages.add("Ficha de Nutricação não localizada ou já excluída.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(nutricaoList);
                responses.messages.add("Fichas de Nutricação não localizadas ou já excluídas.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}