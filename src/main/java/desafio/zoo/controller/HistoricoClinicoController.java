package desafio.zoo.controller;

import desafio.zoo.model.HistoricoClinico;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class HistoricoClinicoController {

    public HistoricoClinico historicoClinico;


    public HistoricoClinico getHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("animal = ?1 ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (historicoClinico == null || !historicoClinico.isAtivo) {
            throw new BadRequestException("Histórico Clínico não localizado.");
        }
        return historicoClinico;
    }

    public void addHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (historicoClinico == null || !historicoClinico.isAtivo) {
            historicoClinico = new HistoricoClinico();
            historicoClinico.animal = pHistoricoClinico.animal;
            historicoClinico.pelagem = pHistoricoClinico.pelagem;
            historicoClinico.diagnosticoInicial = pHistoricoClinico.diagnosticoInicial;
            historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            historicoClinico.pulso = pHistoricoClinico.pulso;
            historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            historicoClinico.frequenciaCariaca = pHistoricoClinico.frequenciaCariaca;
            historicoClinico.terapiaPosCiclo = pHistoricoClinico.terapiaPosCiclo;
            historicoClinico.observacao = pHistoricoClinico.observacao;
            historicoClinico.isInternamento = pHistoricoClinico.isInternamento;
            historicoClinico.internamento = pHistoricoClinico.internamento;
            historicoClinico.dataAlta = pHistoricoClinico.dataAlta;
            historicoClinico.laudoNecroscopico = pHistoricoClinico.laudoNecroscopico;
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

        historicoClinico = HistoricoClinico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (!(historicoClinico == null) && historicoClinico.animal.equals(pHistoricoClinico.animal) && historicoClinico.isAtivo) {
            if (!Objects.equals(historicoClinico.pelagem, pHistoricoClinico.pelagem)) {
                historicoClinico.pelagem = pHistoricoClinico.pelagem;
            }
            if (!historicoClinico.diagnosticoInicial.equals(pHistoricoClinico.diagnosticoInicial)) {
                historicoClinico.diagnosticoInicial = pHistoricoClinico.diagnosticoInicial;
            }
            if (!historicoClinico.temperaturaAnimal.equals(pHistoricoClinico.temperaturaAnimal)) {
                historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            }
            if (!historicoClinico.pulso.equals(pHistoricoClinico.pulso)) {
                historicoClinico.pulso = pHistoricoClinico.pulso;
            }
            if (!historicoClinico.frequenciaRespiratoria.equals(pHistoricoClinico.frequenciaRespiratoria)) {
                historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            }
            if (!historicoClinico.frequenciaCariaca.equals(pHistoricoClinico.frequenciaCariaca)) {
                historicoClinico.frequenciaCariaca = pHistoricoClinico.frequenciaCariaca;
            }
            if (!historicoClinico.terapiaPosCiclo.equals(pHistoricoClinico.terapiaPosCiclo)) {
                historicoClinico.terapiaPosCiclo = pHistoricoClinico.terapiaPosCiclo;
            }
            if (!historicoClinico.observacao.equals(pHistoricoClinico.observacao)) {
                historicoClinico.observacao = pHistoricoClinico.observacao;
            }
            if (!historicoClinico.isInternamento.equals(pHistoricoClinico.isInternamento)) {
                historicoClinico.isInternamento = pHistoricoClinico.isInternamento;
            }
            if (!historicoClinico.internamento.equals(pHistoricoClinico.internamento)) {
                historicoClinico.internamento = pHistoricoClinico.internamento;
            }
            if (!historicoClinico.dataAlta.equals(pHistoricoClinico.dataAlta)) {
                historicoClinico.dataAlta = pHistoricoClinico.dataAlta;
            }
            if (!historicoClinico.laudoNecroscopico.equals(pHistoricoClinico.laudoNecroscopico)) {
                historicoClinico.laudoNecroscopico = pHistoricoClinico.laudoNecroscopico;
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

    public void deleteHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico) {

        historicoClinico = HistoricoClinico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (!(historicoClinico == null) && historicoClinico.animal.equals(pHistoricoClinico.animal) && (historicoClinico.isAtivo)) {
            historicoClinico.isAtivo = false;
            historicoClinico.usuarioAcao = "usuario que deletou";
            historicoClinico.systemDateDeleted = new Date();
            historicoClinico.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o Histórico Clínico.");
        }
    }
}
