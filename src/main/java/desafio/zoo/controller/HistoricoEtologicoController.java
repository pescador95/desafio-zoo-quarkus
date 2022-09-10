package desafio.zoo.controller;

import desafio.zoo.model.HistoricoEtologico;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
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

        historicoEtologico = HistoricoEtologico.find("animal = ?1 ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();

        if (historicoEtologico == null || !historicoEtologico.isAtivo) {
            throw new BadRequestException("Histórico Etológico não localizado.");
        }
        return historicoEtologico;
    }


    public List<HistoricoEtologico> getHistoricoEtologicoListAtivos() {

        historicoEtologicoList = HistoricoEtologico.list("isAtivo = true ORDER BY id DESC");

        if (historicoEtologicoList.isEmpty()) {
            throw new BadRequestException("Históricos Etológicos não localizados.");
        }
        return historicoEtologicoList;
    }

    public List<HistoricoEtologico> getHistoricoEtologicoListInativos() {

        historicoEtologicoList = HistoricoEtologico.list("isAtivo = false ORDER BY id DESC");

        if (historicoEtologicoList.isEmpty()) {
            throw new BadRequestException("Históricos Etológicos inativos não localizados.");
        }
        return historicoEtologicoList;
    }

    public void addHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico) {

        historicoEtologico = HistoricoEtologico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();

        if (historicoEtologico == null || !historicoEtologico.isAtivo) {
            historicoEtologico = new HistoricoEtologico();
            historicoEtologico.animal = pHistoricoEtologico.animal;
            historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;
            historicoEtologico.nomeEtologico   = pHistoricoEtologico.nomeEtologico  ;
            historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
            historicoEtologico.dataAcao = pHistoricoEtologico.dataAcao;
            historicoEtologico.systemDateDeleted = pHistoricoEtologico.systemDateDeleted;
            historicoEtologico.isAtivo = true;
            historicoEtologico.usuario = pHistoricoEtologico.usuario;
            historicoEtologico.usuarioAcao = "";
            historicoEtologico.dataAcao = new Date();

            historicoEtologico.persist();

        } else {
            throw new BadRequestException("HistoricoEtologico já cadastrado!");
        }

    }

    public void updateHistoricoEtologico(@NotNull HistoricoEtologico pHistoricoEtologico) {

        historicoEtologico = HistoricoEtologico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();

        if (!(historicoEtologico == null) && historicoEtologico.animal.equals(pHistoricoEtologico.animal) && historicoEtologico.isAtivo) {
            if (!Objects.equals(historicoEtologico.dataEtologico, pHistoricoEtologico.dataEtologico)) {
                historicoEtologico.dataEtologico = pHistoricoEtologico.dataEtologico;
            }
            if (!historicoEtologico.nomeEtologico.equals(pHistoricoEtologico.nomeEtologico)) {
                historicoEtologico.nomeEtologico = pHistoricoEtologico.nomeEtologico;
            }
            if (!historicoEtologico.descricaoEtologico.equals(pHistoricoEtologico.descricaoEtologico)) {
                historicoEtologico.descricaoEtologico = pHistoricoEtologico.descricaoEtologico;
            }
            if (historicoEtologico.usuario.equals(pHistoricoEtologico.usuario)) {
                historicoEtologico.usuario = pHistoricoEtologico.usuario;
            }
            historicoEtologico.dataAcao = new Date();
            historicoEtologico.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Histórico Etológico.");

        }
    }

    public void deleteHistoricoEtologico(@NotNull List<HistoricoEtologico> historicoEtologicoList) {

        historicoEtologicoList.forEach((pHistoricoEtologico) -> {
            historicoEtologico = HistoricoEtologico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoEtologico.animal).firstResult();

            if (historicoEtologico != null) {
                historicoEtologico.isAtivo = Boolean.FALSE;
                historicoEtologico.dataAcao = new Date();
                historicoEtologico.usuarioAcao = "usuario que deletou";
                historicoEtologico.systemDateDeleted = new Date();
                historicoEtologico.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar o Histórico Etológico.");
            }
        });
    }
}