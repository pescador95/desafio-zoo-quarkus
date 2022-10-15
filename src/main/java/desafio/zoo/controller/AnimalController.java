package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Usuario;
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

public class AnimalController {

    public Animal animal;
    public void addAnimal(@NotNull Animal pAnimal, String email) {

        animal = Animal.find("identificacao = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.identificacao).firstResult();

        if (animal == null) {
            animal = new Animal();

            if (pAnimal.origem != null) {
                animal.origem = pAnimal.origem;
            } else {
                throw new BadRequestException("Por favor, preencha a origem do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.nomeComum != null) {
                animal.nomeComum = pAnimal.nomeComum;
            } else {
                throw new BadRequestException("Por favor, preencha o nome comum do Animal corretamente!"); //TODO organizar mensagem
            }

            if (pAnimal.nomeCientifico != null) {
                animal.nomeCientifico = pAnimal.nomeCientifico;
            } else {
                throw new BadRequestException("Por favor, preencha a nome científico do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.nomeApelido != null) {
                animal.nomeApelido = pAnimal.nomeApelido;
            } else {
                throw new BadRequestException("Por favor, preencha o apelido do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.identificacao != null) {
                animal.identificacao = pAnimal.identificacao;
            } else {
                throw new BadRequestException("Por favor, preencha a identificação do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.sexo != null) {
                animal.sexo = pAnimal.sexo;
            } else {
                throw new BadRequestException("Por favor, informe o sexo do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.idade != null) {
                animal.idade = pAnimal.idade;
            } else {
                throw new BadRequestException("Por favor, preencha a idade do Animal corretamente!"); //TODO organizar mensagem
            }
            if (pAnimal.dataEntrada != null) {
                animal.dataEntrada = pAnimal.dataEntrada;
            } else {
                throw new BadRequestException("Por favor, preencha a data de entrada do Animal corretamente!"); //TODO organizar mensagem
            }

            animal.isAtivo = Boolean.TRUE;
            animal.usuario = Usuario.find("email = ?1", email).firstResult();
            animal.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
            animal.dataAcao = new Date();

            animal.persist();

        } else {
            throw new BadRequestException("Animal já cadastrado!");//TODO organizar mensagem
        }
    }

    public void updateAnimal(@NotNull Animal pAnimal, String email) {

        animal = Animal.find("id = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.id).firstResult();

        if (animal != null) {

            if (pAnimal.nomeComum == null && pAnimal.nomeCientifico == null && pAnimal.nomeApelido == null && pAnimal.identificacao == null && pAnimal.sexo == null && pAnimal.dataEntrada == null && pAnimal.idade == null && pAnimal.origem == null) {
                throw new BadRequestException("Informe os dados para atualizar o Animal.");//TODO organizar mensagem
            } else {
                if (pAnimal.nomeComum != null) {
                    if (!animal.nomeComum.equals(pAnimal.nomeComum)) {
                        animal.nomeComum = pAnimal.nomeComum;
                    }
                }
                if (pAnimal.nomeCientifico != null) {
                    if (!animal.nomeCientifico.equals(pAnimal.nomeCientifico)) {
                        animal.nomeCientifico = pAnimal.nomeCientifico;
                    }
                }
                if (pAnimal.nomeApelido != null) {
                    if (!animal.nomeApelido.equals(pAnimal.nomeApelido)) {
                        animal.nomeApelido = pAnimal.nomeApelido;
                    }
                }
                if (pAnimal.identificacao != null) {
                    if (!animal.identificacao.equals(pAnimal.identificacao)) {
                        animal.identificacao = pAnimal.identificacao;
                    }
                }
                if (pAnimal.sexo != null) {
                    if (animal.sexo.equals(pAnimal.sexo)) {
                        animal.sexo = pAnimal.sexo;
                    }
                }
                if (pAnimal.dataEntrada != null) {
                    if (!Objects.equals(animal.dataEntrada, pAnimal.dataEntrada)) {
                        animal.dataEntrada = pAnimal.dataEntrada;
                    }
                }
                if (pAnimal.idade != null) {
                    if (animal.idade.equals(pAnimal.idade)) {
                        animal.idade = pAnimal.idade;
                    }
                }
                if (pAnimal.origem != null) {
                    if (animal.origem.equals(pAnimal.origem)) {
                        animal.origem = pAnimal.origem;
                    }
                }
                animal.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                animal.dataAcao = new Date();
                animal.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o animal.");//TODO organizar mensagem
        }
    }

    public void deleteAnimal(@NotNull List<Long> pListIdAnimal, String email) {

        pListIdAnimal.forEach((pAnimal) -> {
            Animal animal = Animal.find("id = ?1 and isAtivo = true ORDER BY id DESC", pAnimal).firstResult();

            if (animal != null) {
                animal.isAtivo = Boolean.FALSE;
                animal.dataAcao = new Date();
               animal.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                animal.systemDateDeleted = new Date();
                animal.persist();
            } else {
                if (pListIdAnimal.size() <= 1) {
                    throw new NotFoundException("Animal não localizados ou já reativado.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Animais não localizados ou já reativados.");//TODO organizar mensagem
                }
            }
        });
    }

    public void reactivateAnimal(@NotNull List<Long> pListIdAnimal, String email) {

        pListIdAnimal.forEach((pAnimal) -> {
            Animal animal = Animal.find("id = ?1 and isAtivo = false ORDER BY id DESC", pAnimal).firstResult();

            if (animal != null) {
                animal.isAtivo = Boolean.TRUE;
                animal.dataAcao = new Date();
                animal.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                animal.systemDateDeleted = null;
                animal.persist();
            } else {
                if (pListIdAnimal.size() <= 1) {
                    throw new NotFoundException("Animal não localizado ou já reativado.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Animais não localizados ou já reativados.");//TODO organizar mensagem
                }
            }
        });
    }

}

