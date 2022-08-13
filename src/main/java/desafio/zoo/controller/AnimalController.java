package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class AnimalController {

    public Animal animal;


    public Animal getAnimal(@NotNull Animal pAnimal) {

        animal = Animal.find("identificacao = ?1 ORDER BY id DESC", pAnimal.identificacao).firstResult();

        if (animal == null || !animal.isAtivo) {
            throw new BadRequestException("Animal não localizado.");
        }
        return animal;
    }

    public void addAnimal(@NotNull Animal pAnimal) {

        animal = Animal.find("identificacao = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.identificacao).firstResult();

        if (animal == null || !animal.isAtivo) {
            animal = new Animal();
            animal.nomeComum = pAnimal.nomeComum;
            animal.nomeCientifico = pAnimal.nomeCientifico;
            animal.nomeApelido = pAnimal.nomeApelido;
            animal.sexo = pAnimal.sexo;
            animal.idade = pAnimal.idade;
            animal.isAtivo = true;
            animal.usuarioAcao = "";
            animal.dataAcao = new Date();

            animal.persist();

        } else {
            throw new BadRequestException("animal já cadastrada!");
        }

    }

    public void updateAnimal(@NotNull Animal pAnimal) {

        animal = Animal.find("identificacao = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.identificacao).firstResult();

        if (!(animal == null) && animal.identificacao.equals(pAnimal.identificacao) && animal.isAtivo) {
            if (!animal.nomeComum.equals(pAnimal.nomeComum)) {
                animal.nomeComum = pAnimal.nomeComum;
            }
            if (!animal.nomeCientifico.equals(pAnimal.nomeCientifico)) {
                animal.nomeCientifico = pAnimal.nomeCientifico;
            }
            if (!animal.nomeApelido.equals(pAnimal.nomeApelido)) {
                animal.nomeApelido = pAnimal.nomeApelido;
            }
            if (animal.sexo != pAnimal.sexo) {
                animal.sexo = pAnimal.sexo;
            }
            if (!Objects.equals(animal.dataEntrada, pAnimal.dataEntrada)) {
                animal.dataEntrada = pAnimal.dataEntrada;
            }
            if (animal.idade!= pAnimal.idade) {
                animal.idade = pAnimal.idade;
            }
            animal.dataAcao = new Date();
            animal.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o animal.");

        }
    }

    public void deleteAnimal(@NotNull Animal pAnimal) {

        animal = Animal.find("identificacao = ?1 and isAtivo = true ORDER BY id DESC", pAnimal.identificacao).firstResult();

        if (!(animal == null) && animal.identificacao.equals(pAnimal.identificacao) && (animal.isAtivo)) {
            animal.isAtivo = false;
            animal.usuarioAcao = "usuario que deletou";
            animal.systemDateDeleted = new Date();
            animal.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o animal.");
        }
    }
}
