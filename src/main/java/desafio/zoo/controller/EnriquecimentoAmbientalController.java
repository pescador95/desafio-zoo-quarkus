package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.EnriquecimentoAmbiental;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
@Transactional

public class EnriquecimentoAmbientalController {

    public EnriquecimentoAmbiental enriquecimentoAmbiental;
    public Animal animal;
    Responses responses;

    Usuario usuarioAuth;

    public Response addEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental,
            String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find(
                "animal = ?1 and dataEnriquecimento = ?2 and nomeEnriquecimento = ?3 and descricaoEnriquecimento = ?4 and isAtivo = true ORDER BY id DESC",
                pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento,
                pEnriquecimentoAmbiental.nomeEnriquecimento, pEnriquecimentoAmbiental.descricaoEnriquecimento)
                .firstResult();
        animal = Animal.find("id = ?1", pEnriquecimentoAmbiental.animal.id).firstResult();

        if (animal != null){
            if (enriquecimentoAmbiental == null) {
                enriquecimentoAmbiental = new EnriquecimentoAmbiental();

                if (pEnriquecimentoAmbiental.animal != null) {
                    enriquecimentoAmbiental.animal = Animal.findById(pEnriquecimentoAmbiental.animal.id);
                } else {
                    responses.messages.add("Por favor, preencha o Animal do Enriquecimento Ambiental corretamente!");
                }
                if (pEnriquecimentoAmbiental.dataEnriquecimento != null) {
                    enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;

                } else {
                    responses.messages.add("Por favor, preencha a Data do Enriquecimento Ambiental corretamente!");
                }
                if (pEnriquecimentoAmbiental.nomeEnriquecimento != null) {
                    enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;

                } else {
                    responses.messages.add("Por favor, preencha o Nome do Enriquecimento Ambiental corretamente!");
                }

                if (pEnriquecimentoAmbiental.descricaoEnriquecimento != null) {
                    enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;

                } else {
                    responses.messages.add(
                            "Por favor, preencha a Descrição do Enriquecimento Ambiental corretamente!");
                }
                if (responses.messages.size() < 1) {
                    enriquecimentoAmbiental.animal = animal;
                    enriquecimentoAmbiental.nomeAnimal = animal.nomeApelido;
                    enriquecimentoAmbiental.isAtivo = Boolean.TRUE;
                    enriquecimentoAmbiental.usuario = usuarioAuth;
                    enriquecimentoAmbiental.usuarioAcao = usuarioAuth;
                    enriquecimentoAmbiental.usuarioNome = usuarioAuth.nome;
                    enriquecimentoAmbiental.usuarioAcaoNome = usuarioAuth.nome;
                    enriquecimentoAmbiental.dataAcao = new Date();

                    enriquecimentoAmbiental.persist();

                    responses.status = 200;
                    responses.data = enriquecimentoAmbiental;
                    responses.messages.add("Enriquecimento Ambiental cadastrado com sucesso!");

                } else {
                    return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
                }
                return Response.ok(responses).status(Response.Status.CREATED).build();
            } else {
                responses.status = 500;
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental já cadastrado!");
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            responses.status = 500;
            responses.data = pEnriquecimentoAmbiental;
            responses.messages.add("Por favor, informar o Animal do Enriquecimento Ambiental.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response updateEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental,
            String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            enriquecimentoAmbiental = EnriquecimentoAmbiental
                    .find("id = ?1 and isAtivo = true ORDER BY id DESC", pEnriquecimentoAmbiental.id).firstResult();
            if (pEnriquecimentoAmbiental.dataEnriquecimento == null
                    && pEnriquecimentoAmbiental.nomeEnriquecimento == null
                    && pEnriquecimentoAmbiental.descricaoEnriquecimento == null) {
                responses.messages.add("Informe os dados para atualizar o Enriquecimento Ambiental.");
            } else {
                if (pEnriquecimentoAmbiental.dataEnriquecimento != null
                        && enriquecimentoAmbiental.dataEnriquecimento != null) {
                    if (!Objects.equals(enriquecimentoAmbiental.dataEnriquecimento,
                            pEnriquecimentoAmbiental.dataEnriquecimento)) {
                        enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;
                    }
                }
                if (pEnriquecimentoAmbiental.nomeEnriquecimento != null
                        && enriquecimentoAmbiental.nomeEnriquecimento != null) {
                    if (!enriquecimentoAmbiental.nomeEnriquecimento
                            .equals(pEnriquecimentoAmbiental.nomeEnriquecimento)) {
                        enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;
                    }
                }
                if (pEnriquecimentoAmbiental.descricaoEnriquecimento != null
                        && enriquecimentoAmbiental.descricaoEnriquecimento != null) {
                    if (!enriquecimentoAmbiental.descricaoEnriquecimento
                            .equals(pEnriquecimentoAmbiental.descricaoEnriquecimento)) {
                        enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;
                    }
                }
                if (pEnriquecimentoAmbiental.animal != null && enriquecimentoAmbiental.animal != null) {
                    if (!enriquecimentoAmbiental.animal
                            .equals(pEnriquecimentoAmbiental.animal)) {
                        enriquecimentoAmbiental.animal = Animal.findById(pEnriquecimentoAmbiental.animal.id);
                    }
                }

                enriquecimentoAmbiental.nomeAnimal = enriquecimentoAmbiental.animal.nomeApelido;
                enriquecimentoAmbiental.usuarioAcao = usuarioAuth;
                enriquecimentoAmbiental.usuarioAcaoNome = usuarioAuth.nome;
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.persist();

                responses.status = 200;
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = enriquecimentoAmbiental;
            responses.messages.add("Não foi possível atualizar o Enriquecimento Ambiental.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteEnriquecimentoAmbiental(List<Long> pListEnriquecimentoAmbiental, String email) {

        List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;
        List<EnriquecimentoAmbiental> enriquecimentoAmbientalListAux = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        enriquecimentoAmbientalList = EnriquecimentoAmbiental.list("id in ?1 and isAtivo = true", pListEnriquecimentoAmbiental);
        Integer countList = enriquecimentoAmbientalList.size();

        try {
            enriquecimentoAmbientalList.forEach((enriquecimentoAmbiental) -> {

                enriquecimentoAmbiental.isAtivo = Boolean.FALSE;
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.usuarioAcao = usuarioAuth;
                enriquecimentoAmbiental.usuarioAcaoNome = usuarioAuth.nome;
                enriquecimentoAmbiental.systemDateDeleted = new Date();
                enriquecimentoAmbiental.persist();
                enriquecimentoAmbientalListAux.add(enriquecimentoAmbiental);
            });
            responses.status = 200;
            if (countList <= 1) {
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental excluído com sucesso.");
            } else {
                responses.dataList = Collections.singletonList(enriquecimentoAmbientalListAux);
                responses.messages.add(countList + " Enriquecimentos Ambientais excluídos com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            if (countList <= 1) {
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental não localizado ou já excluído.");
            } else {
                responses.dataList = Collections.singletonList(enriquecimentoAmbientalListAux);
                responses.messages.add("Enriquecimentos Ambientais não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateEnriquecimentoAmbiental(List<Long> pListEnriquecimentoAmbiental, String email) {


        List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;
        List<EnriquecimentoAmbiental> enriquecimentoAmbientalListAux = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        enriquecimentoAmbientalList = EnriquecimentoAmbiental.list("id in ?1 and isAtivo = false", pListEnriquecimentoAmbiental);
        Integer countList = enriquecimentoAmbientalList.size();

        try {
            enriquecimentoAmbientalList.forEach((enriquecimentoAmbiental) -> {

                enriquecimentoAmbiental.isAtivo = Boolean.TRUE;
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.usuarioAcao = usuarioAuth;
                enriquecimentoAmbiental.usuarioAcaoNome = usuarioAuth.nome;
                enriquecimentoAmbiental.systemDateDeleted = null;
                enriquecimentoAmbiental.persist();
                enriquecimentoAmbientalListAux.add(enriquecimentoAmbiental);
            });
            responses.status = 200;
            if (countList <= 1) {
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental reativado com sucesso.");
            } else {
                responses.dataList = Collections.singletonList(enriquecimentoAmbientalListAux);
                responses.messages.add(countList + " Enriquecimentos Ambientais reativados com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            if (countList <= 1) {
                responses.data = enriquecimentoAmbiental;
                responses.messages.add("Enriquecimento Ambiental não localizado ou já reativado.");
            } else {
                responses.dataList = Collections.singletonList(enriquecimentoAmbientalList);
                responses.messages.add("Enriquecimentos Ambientais não localizados ou já reativados.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
