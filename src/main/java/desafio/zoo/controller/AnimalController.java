package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.EnriquecimentoAmbiental;
import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.HistoricoEtologico;
import desafio.zoo.model.Medicacao;
import desafio.zoo.model.Nutricao;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
@Transactional

public class AnimalController {

    public Animal animal;
    Responses responses;
    Usuario usuarioAuth;
    EnriquecimentoAmbiental enriquecimentoAmbiental;
    HistoricoClinico historicoClinico;
    HistoricoEtologico historicoEtologico;
    Medicacao medicacao;
    Nutricao nutricao;

    public Response addAnimal(@NotNull Animal pAnimal, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        animal = Animal.find("identificacao = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.identificacao)
                .firstResult();

        if (animal == null) {
            animal = new Animal();

            if (pAnimal.origem != null) {
                animal.origem = pAnimal.origem;
            } else {
                responses.messages.add("Por favor, preencha a origem do Animal corretamente!");
            }
            if (pAnimal.orgao != null) {
                animal.orgao = pAnimal.orgao;
            } else {
                responses.messages.add("Por favor, preencha o orgão do Animal corretamente!");
            }
            if (pAnimal.nomeComum != null) {
                animal.nomeComum = pAnimal.nomeComum;
            } else {
                responses.messages.add("Por favor, preencha o nome comum do Animal corretamente!");
            }

            if (pAnimal.nomeCientifico != null) {
                animal.nomeCientifico = pAnimal.nomeCientifico;
            } else {
                responses.messages.add("Por favor, preencha o nome científico do Animal corretamente!");
            }
            if (pAnimal.nomeApelido != null) {
                animal.nomeApelido = pAnimal.nomeApelido;
            } else {
                responses.messages.add("Por favor, preencha o apelido do Animal corretamente!");
            }
            if (pAnimal.identificacao != null) {
                animal.identificacao = pAnimal.identificacao;
            } else {
                responses.messages.add("Por favor, preencha a identificação do Animal corretamente!");
            }
            if (pAnimal.sexo != null) {
                animal.sexo = pAnimal.sexo;
            } else {
                responses.messages.add("Por favor, informe o sexo do Animal corretamente!");
            }
            if (pAnimal.idade != null) {
                animal.idade = pAnimal.idade;
            } else {
                responses.messages.add("Por favor, preencha a idade do Animal corretamente!");
            }
            if (pAnimal.dataEntrada != null) {
                animal.dataEntrada = pAnimal.dataEntrada;
            } else {
                responses.messages.add("Por favor, preencha a data de entrada do Animal corretamente!");
            }

            if (responses.messages.size() < 1) {
                animal.isAtivo = Boolean.TRUE;
                animal.usuario = usuarioAuth;
                animal.usuarioAcao = usuarioAuth;
                animal.usuarioNome = usuarioAuth.nome;
                animal.usuarioAcaoNome = usuarioAuth.nome;
                animal.dataAcao = new Date();

                animal.persist();

                responses.status = 201;
                responses.data = animal;
                responses.messages.add("Animal Cadastrado com sucesso!");
            } else {
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 400;
            responses.data = animal;
            responses.messages.add("Animal já cadastrado!");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response updateAnimal(@NotNull Animal pAnimal, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {

            animal = Animal.find("id = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pAnimal.nomeComum == null && pAnimal.nomeCientifico == null && pAnimal.nomeApelido == null
                    && pAnimal.identificacao == null && pAnimal.sexo == null && pAnimal.dataEntrada == null
                    && pAnimal.idade == null && pAnimal.origem == null) {
                responses.status = 500;
                responses.data = animal;
                responses.messages.add("Informe os dados para atualizar o Animal.");
            } else {
                if (pAnimal.nomeComum != null && animal.nomeComum != null) {
                    if (!animal.nomeComum.equals(pAnimal.nomeComum)) {
                        animal.nomeComum = pAnimal.nomeComum;
                    }
                }
                if (pAnimal.nomeCientifico != null && animal.nomeCientifico != null) {
                    if (!animal.nomeCientifico.equals(pAnimal.nomeCientifico)) {
                        animal.nomeCientifico = pAnimal.nomeCientifico;
                    }
                }
                if (pAnimal.nomeApelido != null && animal.nomeApelido != null) {
                    if (!animal.nomeApelido.equals(pAnimal.nomeApelido)) {
                        animal.nomeApelido = pAnimal.nomeApelido;
                    }
                }
                if (pAnimal.identificacao != null && animal.identificacao != null) {
                    if (!animal.identificacao.equals(pAnimal.identificacao)) {
                        animal.identificacao = pAnimal.identificacao;
                    }
                }
                if (pAnimal.sexo != null && animal.sexo != null) {
                    if (!animal.sexo.equals(pAnimal.sexo)) {
                        animal.sexo = pAnimal.sexo;
                    }
                }
                if (pAnimal.dataEntrada != null && animal.dataEntrada != null) {
                    if (!Objects.equals(animal.dataEntrada, pAnimal.dataEntrada)) {
                        animal.dataEntrada = pAnimal.dataEntrada;
                    }
                }
                if (pAnimal.idade != null && animal.idade != null) {
                    if (!animal.idade.equals(pAnimal.idade)) {
                        animal.idade = pAnimal.idade;
                    }
                }
                if (pAnimal.origem != null && animal.origem != null) {
                    if (!animal.origem.equals(pAnimal.origem)) {
                        animal.origem = pAnimal.origem;
                    }
                }
                if (pAnimal.orgao != null && animal.orgao != null) {
                    if (!animal.orgao.equals(pAnimal.orgao)) {
                        animal.orgao = pAnimal.orgao;
                    }
                }
                animal.usuarioAcao = usuarioAuth;
                animal.usuarioAcaoNome = usuarioAuth.nome;
                animal.dataAcao = new Date();
                animal.persist();

                responses.status = 200;
                responses.data = animal;
                responses.messages.add("Animal atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = animal;
            responses.messages.add("Não foi possível atualizar o animal.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteAnimal(@NotNull List<Long> pListIdAnimal, String email) {

        Integer countList = pListIdAnimal.size();
        List<Animal> animalList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdAnimal.forEach((pAnimal) -> {

                Animal animal = Animal.find("id = ?1 and isAtivo = true ORDER BY id DESC", pAnimal).firstResult();
                animal.isAtivo = Boolean.FALSE;
                animal.dataAcao = new Date();
                animal.usuarioAcao = usuarioAuth;
                animal.usuarioAcaoNome = usuarioAuth.nome;
                animal.systemDateDeleted = new Date();
                animal.persist();
                animalList.add(animal);
            });

            if (pListIdAnimal.size() <= 1) {
                responses.status = 200;
                responses.data = animal;
                responses.messages.add("Animal excluído com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(animalList);
                responses.messages.add(countList + " Animais excluídos com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdAnimal.size() <= 1) {
                responses.status = 500;
                responses.data = animal;
                responses.messages.add("Animal não localizado ou já excluído.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(animalList);
                responses.messages.add("Animais não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateAnimal(@NotNull List<Long> pListIdAnimal, String email) {

        Integer countList = pListIdAnimal.size();
        List<Animal> animalList = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        try {
            pListIdAnimal.forEach((pAnimal) -> {
                Animal animal = Animal.find("id = ?1 and isAtivo = false ORDER BY id DESC", pAnimal).firstResult();

                animal.isAtivo = Boolean.TRUE;
                animal.dataAcao = new Date();
                animal.usuarioAcao = usuarioAuth;
                animal.usuarioAcaoNome = usuarioAuth.nome;
                animal.systemDateDeleted = null;
                animal.persist();
                animalList.add(animal);
            });
            if (pListIdAnimal.size() <= 1) {
                responses.status = 200;
                responses.data = animal;
                responses.messages.add("Animal reativado com sucesso!");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(animalList);
                responses.messages.add(countList + " Animais reativados com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdAnimal.size() <= 1) {
                responses.status = 500;
                responses.data = animal;
                responses.messages.add("Animal não localizado ou já reativado.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(animalList);
                responses.messages.add("Animais não localizados ou já reativados.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}