package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoClinico;
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

public class HistoricoClinicoController {

    public HistoricoClinico historicoClinico;
    public Animal animal;
    Responses responses;
    Usuario usuarioAuth;

    public Response addHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        historicoClinico = HistoricoClinico
                .find("animal = ?1 and isAtivo = true and dataHistoricoClinico =?2 ORDER BY id DESC", pHistoricoClinico.animal, pHistoricoClinico.dataHistoricoClinico).firstResult();
        animal = Animal.find("id = ?1", pHistoricoClinico.animal.id).firstResult();

        if (historicoClinico == null) {
            historicoClinico = new HistoricoClinico();

            if (pHistoricoClinico.animal != null) {
                historicoClinico.animal = Animal.findById(pHistoricoClinico.animal.id);
            } else {
                responses.messages.add("Por favor, informar o Animal do Histórico Clínico.");
            }
            if (pHistoricoClinico.etco2 != null) {
                historicoClinico.etco2 = pHistoricoClinico.etco2;
            } else {
                responses.messages.add("Por favor, informar o etco2 do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.spo2 != null) {
                historicoClinico.spo2 = pHistoricoClinico.spo2;
            } else {
                responses.messages.add("Por favor, informar o spo2 do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.temperaturaAnimal != null) {
                historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            } else {
                responses.messages.add("Por favor, informar a temperatura do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.ps != null) {
                historicoClinico.ps = pHistoricoClinico.ps;
            } else {
                responses.messages.add("Por favor, informar o ps do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.frequenciaRespiratoria != null) {
                historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            } else {
                responses.messages.add("Por favor, informar o frequencia Respiratória do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.frequenciaCardiaca != null) {
                historicoClinico.frequenciaCardiaca = pHistoricoClinico.frequenciaCardiaca;
            } else {
                responses.messages.add("Por favor, informar o frequencia Cardíaca do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.pd != null) {
                historicoClinico.pd = pHistoricoClinico.pd;

            } else {
                responses.messages.add("Por favor, informar o pd do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.observacao != null) {
                historicoClinico.observacao = pHistoricoClinico.observacao;
            } else {
                responses.messages.add("Por favor, informar a observacao do Animal no Histórico Clínico.");
            }
            if (pHistoricoClinico.pm != null) {
                historicoClinico.pm = pHistoricoClinico.pm;
            } else {
                responses.messages.add("Por favor, informar o pm do Animal no Histórico Clínico.");
            }

            if (responses.messages.size() < 1) {
                historicoClinico.dataHistoricoClinico = new Date();
                historicoClinico.nomeAnimal = animal.nomeApelido;
                historicoClinico.isAtivo = Boolean.TRUE;
                historicoClinico.usuario = usuarioAuth;
                historicoClinico.usuarioAcao = usuarioAuth;
                historicoClinico.dataAcao = new Date();

                historicoClinico.persist();

                responses.status = 201;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico com sucesso!");
            } else {
                return Response.ok(responses).status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 500;
            responses.data = historicoClinico;
            responses.messages.add("Histórico Clínico já cadastrado!");
            return Response.ok(responses).status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    public Response updateHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {

            historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pHistoricoClinico.etco2 == null && pHistoricoClinico.temperaturaAnimal == null
                    && pHistoricoClinico.spo2 == null && pHistoricoClinico.frequenciaRespiratoria == null
                    && pHistoricoClinico.frequenciaCardiaca == null && pHistoricoClinico.ps == null
                    && pHistoricoClinico.pd == null && pHistoricoClinico.pm == null) {
                responses.status = 500;
                responses.data = historicoClinico;
                responses.messages.add("Informe os dados para atualizar o Histórico Clínico.");
            } else {
                if (pHistoricoClinico.etco2 != null && historicoClinico.etco2 != null) {
                    if (!historicoClinico.etco2.equals(pHistoricoClinico.etco2)) {
                        historicoClinico.etco2 = pHistoricoClinico.etco2;
                    }
                }
                if (pHistoricoClinico.temperaturaAnimal != null && historicoClinico.temperaturaAnimal != null) {
                    if (!historicoClinico.temperaturaAnimal.equals(pHistoricoClinico.temperaturaAnimal)) {
                        historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
                    }
                }
                if (pHistoricoClinico.spo2 != null && historicoClinico.spo2 != null) {
                    if (!historicoClinico.spo2.equals(pHistoricoClinico.spo2)) {
                        historicoClinico.spo2 = pHistoricoClinico.spo2;
                    }
                }
                if (pHistoricoClinico.frequenciaRespiratoria != null && historicoClinico.frequenciaRespiratoria != null) {
                    if (!historicoClinico.frequenciaRespiratoria.equals(pHistoricoClinico.frequenciaRespiratoria)) {
                        historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
                    }
                }
                if (pHistoricoClinico.frequenciaCardiaca != null && historicoClinico.frequenciaCardiaca != null) {
                    if (!historicoClinico.frequenciaCardiaca.equals(pHistoricoClinico.frequenciaCardiaca)) {
                        historicoClinico.frequenciaCardiaca = pHistoricoClinico.frequenciaCardiaca;
                    }
                }
                if (pHistoricoClinico.ps != null && historicoClinico.ps != null) {
                    if (!historicoClinico.ps.equals(pHistoricoClinico.ps)) {
                        historicoClinico.ps = pHistoricoClinico.ps;
                    }
                }
                if (pHistoricoClinico.observacao != null && historicoClinico.observacao != null) {
                    if (!historicoClinico.observacao.equals(pHistoricoClinico.observacao)) {
                        historicoClinico.observacao = pHistoricoClinico.observacao;
                    }
                }
                if (pHistoricoClinico.pd != null && historicoClinico.pd != null) {
                    if (!historicoClinico.pd.equals(pHistoricoClinico.pd)) {
                        historicoClinico.pd = pHistoricoClinico.pd;
                    }
                }
                if (pHistoricoClinico.pm != null && historicoClinico.pm != null) {
                    if (!historicoClinico.pm.equals(pHistoricoClinico.pm)) {
                        historicoClinico.pm = pHistoricoClinico.pm;
                    }
                }
                if (pHistoricoClinico.animal != null && historicoClinico.animal != null) {
                    if (historicoClinico.animal != null && !historicoClinico.animal.equals(pHistoricoClinico.animal)) {
                        historicoClinico.animal = Animal.findById(pHistoricoClinico.animal.id);
                    }
                }
                historicoClinico.nomeAnimal = historicoClinico.animal.nomeApelido;
                historicoClinico.usuarioAcao = usuarioAuth;
                historicoClinico.dataAcao = new Date();
                historicoClinico.persist();

                responses.status = 200;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = historicoClinico;
            responses.messages.add("Não foi possível atualizar o Histórico Clínico.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteHistoricoClinico(List<Long> pListIdHistoricoClinico, String email) {

        Integer countList = pListIdHistoricoClinico.size();
        List<HistoricoClinico> historicoClinicoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        try {

            pListIdHistoricoClinico.forEach((pHistoricoClinico) -> {

                historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico).firstResult();

                historicoClinico.isAtivo = Boolean.FALSE;
                historicoClinico.dataAcao = new Date();
                historicoClinico.usuarioAcao = usuarioAuth;
                historicoClinico.systemDateDeleted = new Date();
                historicoClinico.persist();
            });

            if (pListIdHistoricoClinico.size() <= 1) {
                responses.status = 200;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico excluído com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoClinicoList);
                responses.messages.add(countList + " Históricos Clínico excluídos com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdHistoricoClinico.size() <= 1) {
                responses.status = 500;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico não localizado ou já excluído.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoClinicoList);
                responses.messages.add(countList + " Históricos Clínico não localizado ou já excluído.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateHistoricoClinico(@NotNull List<Long> pListIdHistoricoClinico, String email) {

        Integer countList = pListIdHistoricoClinico.size();
        List<HistoricoClinico> historicoClinicoList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        try {

            pListIdHistoricoClinico.forEach((pHistoricoClinico) -> {

                historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = false ORDER BY id DESC", pHistoricoClinico).firstResult();

                historicoClinico.isAtivo = Boolean.TRUE;
                historicoClinico.dataAcao = new Date();
                historicoClinico.usuarioAcao = usuarioAuth;
                historicoClinico.systemDateDeleted = new Date();
                historicoClinico.persist();
            });

            if (pListIdHistoricoClinico.size() <= 1) {
                responses.status = 200;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico reativado com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoClinicoList);
                responses.messages.add(countList + " Históricos Clínico reativados com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdHistoricoClinico.size() <= 1) {
                responses.status = 500;
                responses.data = historicoClinico;
                responses.messages.add("Histórico Clínico não localizado ou já reativado.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(historicoClinicoList);
                responses.messages.add(countList + " Históricos Clínico não localizado ou já reativados.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
