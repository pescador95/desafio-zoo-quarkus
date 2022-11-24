package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoEtologico;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
@Transactional

public class HistoricoEtologicoController {

    public HistoricoEtologico historicoEtologico;
    public Animal animal;
    Responses responses;
    Usuario usuarioAuth;

    public Response addHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        historicoEtologico = HistoricoEtologico
                .find("animal = ?1 and isAtivo = true and dataEtologico =? 2 ORDER BY id DESC",
                        pHistoricoEtologico.animal, pHistoricoEtologico.dataEtologico)
                .firstResult();
        animal = Animal.find("id = ?1", pHistoricoEtologico.animal.id).firstResult();

        if (historicoEtologico == null) {
            historicoEtologico = new HistoricoEtologico();

            if (pHistoricoEtologico.animal != null) {
                historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
            } else {
                responses.messages.add("Por favor, informar o Animal do Histórico Etológico.");
            }
            if (pHistoricoEtologico.dataEtologico != null) {
                historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;

            } else {
                responses.messages.add("Por favor, preencha a Data do evento Etológico corretamente!");
            }
            if (pHistoricoEtologico.nomeEtologico != null) {
                historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;

            } else {
                responses.messages.add(
                        "Por favor, preencha o Nome Etológico do Histórico Etológico corretamente!");
            }
            if (pHistoricoEtologico.descricaoEtologico != null) {
                historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
            } else {
                responses.messages.add(
                        "Por favor, preencha a Descriação Etológica do Histórico Etológico corretamente!");
            }

            if (responses.messages.size() < 1) {

                historicoEtologico.nomeAnimal = animal.nomeApelido;
                historicoEtologico.isAtivo = Boolean.TRUE;
                historicoEtologico.usuario = usuarioAuth;
                historicoEtologico.usuarioAcao = usuarioAuth;
                historicoEtologico.usuarioNome = usuarioAuth.nome;
                historicoEtologico.usuarioAcaoNome = usuarioAuth.nome;
                historicoEtologico.dataAcao = new Date();

                historicoEtologico.persist();

                responses.status = 201;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico Cadastrado com sucesso!");
            } else {
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 500;
            responses.data = historicoEtologico;
            responses.messages.add("HistoricoEtologico já cadastrado!");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response updateHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {

            historicoEtologico = HistoricoEtologico
                    .find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pHistoricoEtologico.dataEtologico == null && pHistoricoEtologico.nomeEtologico == null
                    && pHistoricoEtologico.descricaoEtologico == null && pHistoricoEtologico.animal == null) {
                responses.status = 500;
                responses.data = historicoEtologico;
                responses.messages.add("Informe os dados para atualizar o Histórico Etológico.");
            } else {
                if (pHistoricoEtologico.dataEtologico != null && historicoEtologico.dataEtologico != null) {
                    if (!Objects.equals(historicoEtologico.dataEtologico, pHistoricoEtologico.dataEtologico)) {
                        historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;
                    }
                }
                if (pHistoricoEtologico.nomeEtologico != null && historicoEtologico.nomeEtologico != null) {
                    if (!historicoEtologico.nomeEtologico.equals(pHistoricoEtologico.nomeEtologico)) {
                        historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;
                    }
                }
                if (pHistoricoEtologico.descricaoEtologico != null && historicoEtologico.descricaoEtologico != null) {
                    if (!historicoEtologico.descricaoEtologico.equals(pHistoricoEtologico.descricaoEtologico)) {
                        historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
                    }
                }
                if (pHistoricoEtologico.animal != null && historicoEtologico.animal != null) {
                    if (historicoEtologico.animal != null
                            && !historicoEtologico.animal.equals(pHistoricoEtologico.animal)) {
                        historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
                    }
                }
                historicoEtologico.nomeAnimal = historicoEtologico.animal.nomeApelido;
                historicoEtologico.usuarioAcao = usuarioAuth;
                historicoEtologico.usuarioAcaoNome = usuarioAuth.nome;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.persist();

                responses.status = 200;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = historicoEtologico;
            responses.messages.add("Não foi possível atualizar o Histórico Etológico.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteHistoricoEtologico(List<Long> pListHistoricoEtologico, String email) {

        List<HistoricoEtologico> historicoEtologicoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListHistoricoEtologico.forEach((pHistoricoEtologico) -> {

                historicoEtologico = HistoricoEtologico
                        .find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico).firstResult();

                historicoEtologico.isAtivo = Boolean.FALSE;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.usuarioAcao = usuarioAuth;
                historicoEtologico.usuarioAcaoNome = usuarioAuth.nome;
                historicoEtologico.systemDateDeleted = new Date();
                historicoEtologico.persist();
                historicoEtologicoList.add(historicoEtologico);
            });
            if (pListHistoricoEtologico.size() <= 1) {
                responses.status = 200;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico excluído com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoEtologicoList);
                responses.messages.add("Históricos Etológico excluídos com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListHistoricoEtologico.size() <= 1) {
                responses.status = 500;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico não localizado ou já excluído.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(historicoEtologicoList);
                responses.messages.add("Históricos Etológico não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateHistoricoEtologico(List<Long> pListHistoricoEtologico, String email) {

        List<HistoricoEtologico> historicoEtologicoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListHistoricoEtologico.forEach((pHistoricoEtologico) -> {

                historicoEtologico = HistoricoEtologico
                        .find("id = ?1 and isAtivo = false ORDER BY id DESC", pHistoricoEtologico).firstResult();

                historicoEtologico.isAtivo = Boolean.TRUE;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.usuarioAcao = usuarioAuth;
                historicoEtologico.usuarioAcaoNome = usuarioAuth.nome;
                historicoEtologico.systemDateDeleted = new Date();
                historicoEtologico.persist();
                historicoEtologicoList.add(historicoEtologico);
            });
            if (pListHistoricoEtologico.size() <= 1) {
                responses.status = 200;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico reativado com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoEtologicoList);
                responses.messages.add("Históricos Etológico reativados com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListHistoricoEtologico.size() <= 1) {
                responses.status = 500;
                responses.data = historicoEtologico;
                responses.messages.add("Histórico Etológico não localizado ou já reativado.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(historicoEtologicoList);
                responses.messages.add("Históricos Etológico não localizados ou já reativados.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}