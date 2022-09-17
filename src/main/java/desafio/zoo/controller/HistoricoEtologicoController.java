package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoEtologico;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class HistoricoEtologicoController {

    public HistoricoEtologico historicoEtologico;
    List<HistoricoEtologico> historicoEtologicoList = new ArrayList<>();


    public HistoricoEtologico getHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico) {

        historicoEtologico = HistoricoEtologico.find("id = ?1 ORDER BY id DESC", pHistoricoEtologico.id).firstResult();

        if (historicoEtologico == null || !historicoEtologico.isAtivo) {
            throw new NotFoundException("Histórico Etológico não localizado.");
        }
        return historicoEtologico;
    }


    public List<HistoricoEtologico> getHistoricoEtologicoListAtivos() {

        historicoEtologicoList = HistoricoEtologico.list("isAtivo = true ORDER BY id DESC");

        if (historicoEtologicoList.isEmpty()) {
            throw new NotFoundException("Históricos Etológicos não localizados.");
        }
        return historicoEtologicoList;
    }

    public List<HistoricoEtologico> getHistoricoEtologicoListInativos() {

        historicoEtologicoList = HistoricoEtologico.list("isAtivo = false ORDER BY id DESC");

        if (historicoEtologicoList.isEmpty()) {
            throw new NotFoundException("Históricos Etológicos inativos não localizados.");
        }
        return historicoEtologicoList;
    }

    public void addHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico) {

        historicoEtologico = HistoricoEtologico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();

        if (historicoEtologico == null) {
            historicoEtologico = new HistoricoEtologico();

            if (pHistoricoEtologico.animal != null) {
                historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
            } else {
                throw new BadRequestException("Por favor, informar o Animal do Histórico Etológico.");//TODO organizar mensagem
            }
            if (pHistoricoEtologico.dataEtologico != null) {
                historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;

            } else {
                throw new BadRequestException("Por favor, preencha a Data do evento Etológico corretamente!");//TODO organizar mensagem
            }
            if (pHistoricoEtologico.nomeEtologico != null) {
                historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;

            } else {
                throw new BadRequestException("Por favor, preencha o Nome Etológico do Histórico Etológico corretamente!");//TODO organizar mensagem
            }
            if (pHistoricoEtologico.descricaoEtologico != null) {
                historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
            } else {
                throw new BadRequestException("Por favor, preencha a Descriação Etológica do Histórico Etológico corretamente!");//TODO organizar mensagem
            }

            historicoEtologico.isAtivo = Boolean.TRUE;
            historicoEtologico.usuario = Usuario.findById(pHistoricoEtologico.usuario.id);
            historicoEtologico.usuarioAcao = Usuario.findById(pHistoricoEtologico.usuarioAcao.id);
            historicoEtologico.dataAcao = new Date();

            historicoEtologico.persist();

        } else {
            throw new BadRequestException("HistoricoEtologico já cadastrado!");//TODO organizar mensagem
        }

    }

    public void updateHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico) {

        historicoEtologico = HistoricoEtologico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.id).firstResult();

        if (historicoEtologico != null) {
            if (pHistoricoEtologico.dataEtologico == null && pHistoricoEtologico.nomeEtologico == null && pHistoricoEtologico.descricaoEtologico == null && pHistoricoEtologico.animal == null) {
                throw new BadRequestException("Informe os dados para atualizar o Histórico Etológico.");//TODO organizar mensagem
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
                    if (historicoEtologico.animal != null && !historicoEtologico.animal.equals(pHistoricoEtologico.animal)) {
                        historicoEtologico.animal = Animal.findById(pHistoricoEtologico.animal.id);
                    }
                }

                historicoEtologico.usuarioAcao = Usuario.findById(pHistoricoEtologico.usuarioAcao.id);
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Histórico Etológico.");//TODO organizar mensagem

        }
    }

    public void deleteHistoricoEtologico(@NotNull List<HistoricoEtologico> historicoEtologicoList) {

        historicoEtologicoList.forEach((pHistoricoEtologico) -> {
            historicoEtologico = HistoricoEtologico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.id).firstResult();

            if (historicoEtologico != null) {
                historicoEtologico.isAtivo = Boolean.FALSE;
                historicoEtologico.dataAcao = new Date();
                if(pHistoricoEtologico.usuarioAcao != null) {
                    historicoEtologico.usuarioAcao = Usuario.findById(pHistoricoEtologico.usuarioAcao.id);
                }
                historicoEtologico.systemDateDeleted = new Date();
                historicoEtologico.persist();
            } else {
                if (historicoEtologicoList.size() <= 1) {
                    throw new NotFoundException("Histórico Etológico não localizado ou já excluído.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Históricos Etológico não localizados ou já excluídos.");//TODO organizar mensagem
                }
            }
        });
    }


    public void reactivateHistoricoEtologico(@NotNull List<HistoricoEtologico> historicoEtologicoList) {

        historicoEtologicoList.forEach((pHistoricoEtologico) -> {
            historicoEtologico = HistoricoEtologico.find("id = ?1 and isAtivo = false ORDER BY id DESC", pHistoricoEtologico.id).firstResult();

            if (historicoEtologico != null) {
                historicoEtologico.isAtivo = Boolean.TRUE;
                historicoEtologico.dataAcao = new Date();
                if(pHistoricoEtologico.usuarioAcao != null) {
                    historicoEtologico.usuarioAcao = Usuario.findById(pHistoricoEtologico.usuarioAcao.id);
                }
                historicoEtologico.systemDateDeleted = null;
                historicoEtologico.persist();
            } else {
                if (historicoEtologicoList.size() <= 1) {
                    throw new NotFoundException("Histórico Etológico não localizado ou já reativado.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Históricos Etológico não localizados ou já reativados.");//TODO organizar mensagem
                }
            }
        });
    }
}