package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoEtologico;
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

public class HistoricoEtologicoController {

    public HistoricoEtologico historicoEtologico;
    public Animal animal;
    
    public void addHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico, String email) {

        historicoEtologico = HistoricoEtologico
                .find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();
        animal = Animal.find("id = ?1", pHistoricoEtologico.animal.id).firstResult();

        if (historicoEtologico == null) {
            historicoEtologico = new HistoricoEtologico();

            if (pHistoricoEtologico.animal != null) {
                historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
            } else {
                throw new BadRequestException("Por favor, informar o Animal do Histórico Etológico.");
            }
            if (pHistoricoEtologico.dataEtologico != null) {
                historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;

            } else {
                throw new BadRequestException("Por favor, preencha a Data do evento Etológico corretamente!");
            }
            if (pHistoricoEtologico.nomeEtologico != null) {
                historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;

            } else {
                throw new BadRequestException(
                        "Por favor, preencha o Nome Etológico do Histórico Etológico corretamente!");
            }
            if (pHistoricoEtologico.descricaoEtologico != null) {
                historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
            } else {
                throw new BadRequestException(
                        "Por favor, preencha a Descriação Etológica do Histórico Etológico corretamente!");
            }

            historicoEtologico.nomeAnimal = animal.nomeApelido;
            historicoEtologico.isAtivo = Boolean.TRUE;
            historicoEtologico.usuario = Usuario.find("email = ?1", email).firstResult();
            historicoEtologico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
            historicoEtologico.dataAcao = new Date();

            historicoEtologico.persist();

        } else {
            throw new BadRequestException("HistoricoEtologico já cadastrado!");
        }

    }

    public void updateHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico, String email) {

        historicoEtologico = HistoricoEtologico
                .find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.id).firstResult();

        if (historicoEtologico != null) {
            if (pHistoricoEtologico.dataEtologico == null && pHistoricoEtologico.nomeEtologico == null
                    && pHistoricoEtologico.descricaoEtologico == null && pHistoricoEtologico.animal == null) {
                throw new BadRequestException("Informe os dados para atualizar o Histórico Etológico.");
            } else {
                if (pHistoricoEtologico.dataEtologico != null) {
                    if (!Objects.equals(historicoEtologico.dataEtologico, pHistoricoEtologico.dataEtologico)) {
                        historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;
                    }
                }
                if (pHistoricoEtologico.nomeEtologico != null) {
                    if (!historicoEtologico.nomeEtologico.equals(pHistoricoEtologico.nomeEtologico)) {
                        historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;
                    }
                }
                if (pHistoricoEtologico.descricaoEtologico != null) {
                    if (!historicoEtologico.descricaoEtologico.equals(pHistoricoEtologico.descricaoEtologico)) {
                        historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
                    }
                }
                if (pHistoricoEtologico.animal != null) {
                    if (historicoEtologico.animal != null
                            && !historicoEtologico.animal.equals(pHistoricoEtologico.animal)) {
                        historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
                    }
                }


                historicoEtologico.nomeAnimal = historicoEtologico.animal.nomeApelido;
                historicoEtologico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Histórico Etológico.");

        }
    }

    public void deleteHistoricoEtologico(List<Long> pListHistoricoEtologico, String email) {

        pListHistoricoEtologico.forEach((pHistoricoEtologico) -> {
            historicoEtologico = HistoricoEtologico
                    .find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico).firstResult();

            if (historicoEtologico != null) {
                historicoEtologico.isAtivo = Boolean.FALSE;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoEtologico.systemDateDeleted = new Date();
                historicoEtologico.persist();
            } else {
                if (pListHistoricoEtologico.size() <= 1) {
                    throw new NotFoundException("Histórico Etológico não localizado ou já excluído.");
                } else {
                    throw new NotFoundException("Históricos Etológico não localizados ou já excluídos.");
                }
            }
        });
    }

    public void reactivateHistoricoEtologico(List<Long> pListHistoricoEtologico, String email) {

        pListHistoricoEtologico.forEach((pHistoricoEtologico) -> {
            historicoEtologico = HistoricoEtologico
                    .find("id = ?1 and isAtivo = false ORDER BY id DESC", pHistoricoEtologico).firstResult();

            if (historicoEtologico != null) {
                historicoEtologico.isAtivo = Boolean.TRUE;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoEtologico.systemDateDeleted = null;
                historicoEtologico.persist();
            } else {
                if (pListHistoricoEtologico.size() <= 1) {
                    throw new NotFoundException("Histórico Etológico não localizado ou já reativado.");
                } else {
                    throw new NotFoundException("Históricos Etológico não localizados ou já reativados.");
                }
            }
        });
    }
}