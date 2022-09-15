package desafio.zoo.controller;

import desafio.zoo.model.HistoricoClinico;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional

public class HistoricoClinicoController {

    public HistoricoClinico historicoClinico;
    List<HistoricoClinico> historicoClinicoList = new ArrayList<>();



    public HistoricoClinico getHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("id = ?1 ORDER BY id DESC", pHistoricoClinico.id).firstResult();

        if (historicoClinico == null || !historicoClinico.isAtivo) {
            throw new BadRequestException("Histórico Clínico não localizado.");
        }
        return historicoClinico;
    }

    public List<HistoricoClinico> getHistoricoClinicoListAtivos() {

        historicoClinicoList = HistoricoClinico.list("isAtivo = true ORDER BY id DESC");

        if (historicoClinicoList.isEmpty()) {
            throw new BadRequestException("Históricos Clínicos não localizados.");
        }
        return historicoClinicoList;
    }

    public List<HistoricoClinico> getHistoricoClinicoListInativos() {

        historicoClinicoList = HistoricoClinico.list("isAtivo = false ORDER BY id DESC");

        if (historicoClinicoList.isEmpty()) {
            throw new BadRequestException("Históricos Clínicos inativos não localizados.");
        }
        return historicoClinicoList;
    }

    public void addHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (historicoClinico == null || !historicoClinico.isAtivo) {
            historicoClinico = new HistoricoClinico();
            historicoClinico.animal = pHistoricoClinico.animal;
            historicoClinico.etco2 = pHistoricoClinico.etco2;
            historicoClinico.spo2 = pHistoricoClinico.spo2;
            historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            historicoClinico.ps = pHistoricoClinico.ps;
            historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            historicoClinico.frequenciaCariaca = pHistoricoClinico.frequenciaCariaca;
            historicoClinico.pd = pHistoricoClinico.pd;
            historicoClinico.observacao = pHistoricoClinico.observacao;
            historicoClinico.pm = pHistoricoClinico.pm;
            historicoClinico.dataAcao = pHistoricoClinico.dataAcao;
            historicoClinico.systemDateDeleted = pHistoricoClinico.systemDateDeleted;
            historicoClinico.isAtivo = true;
            historicoClinico.usuario = pHistoricoClinico.usuario;
            historicoClinico.usuarioAcao = "";
            historicoClinico.dataAcao = new Date();

            historicoClinico.persist();

        } else {
            throw new BadRequestException("Histórico Clínico já cadastrado!");
        }

    }

    public void updateHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.id).firstResult();

        if (!(historicoClinico == null) && historicoClinico.id.equals(pHistoricoClinico.id) && historicoClinico.isAtivo) {

            if (!historicoClinico.etco2.equals(pHistoricoClinico.etco2)) {
                historicoClinico.etco2 = pHistoricoClinico.etco2;
            }
            if (!historicoClinico.temperaturaAnimal.equals(pHistoricoClinico.temperaturaAnimal)) {
                historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            }
            if (!historicoClinico.spo2.equals(pHistoricoClinico.spo2)) {
                historicoClinico.spo2 = pHistoricoClinico.spo2;
            }
            if (!historicoClinico.frequenciaRespiratoria.equals(pHistoricoClinico.frequenciaRespiratoria)) {
                historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            }
            if (!historicoClinico.frequenciaCariaca.equals(pHistoricoClinico.frequenciaCariaca)) {
                historicoClinico.frequenciaCariaca = pHistoricoClinico.frequenciaCariaca;
            }
            if (!historicoClinico.ps.equals(pHistoricoClinico.ps)) {
                historicoClinico.ps = pHistoricoClinico.ps;
            }
            if (!historicoClinico.observacao.equals(pHistoricoClinico.observacao)) {
                historicoClinico.observacao = pHistoricoClinico.observacao;
            }
            if (!historicoClinico.pd.equals(pHistoricoClinico.pd)) {
                historicoClinico.pd = pHistoricoClinico.pd;
            }
            if (!historicoClinico.pm.equals(pHistoricoClinico.pm)) {
                historicoClinico.pm = pHistoricoClinico.pm;
            }
            if (historicoClinico.usuario.equals(pHistoricoClinico.usuario)) {
                historicoClinico.usuario = pHistoricoClinico.usuario;
            }
            historicoClinico.dataAcao = new Date();
            historicoClinico.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Histórico Clínico.");

        }
    }

    public void deleteHistoricoClinico(@NotNull List<HistoricoClinico> historicoClinicoList) {

        historicoClinicoList.forEach((pHistoricoClinico) -> {
            historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.id).firstResult();

            if (historicoClinico != null) {
                historicoClinico.isAtivo = Boolean.FALSE;
                historicoClinico.dataAcao = new Date();
                historicoClinico.usuarioAcao = "usuario que deletou";
                historicoClinico.systemDateDeleted = new Date();
                historicoClinico.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar o Histórico Clínico.");
            }
        });
    }
}