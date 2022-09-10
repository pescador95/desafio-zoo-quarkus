package desafio.zoo.controller;

import desafio.zoo.model.Internamento;
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

public class InternamentoController {

    public Internamento internamento;
    List<Internamento> internamentoList = new ArrayList<>();


    public Internamento getInternamento(@NotNull Internamento pInternamento) {

        internamento = Internamento.find("monitoracaoId = ?1 ORDER BY id DESC", pInternamento.monitoracao).firstResult();

        if (internamento == null || !internamento.isAtivo) {
            throw new BadRequestException("Laudo não localizado.");
        }
        return internamento;
    }

    public List<Internamento> getInternamentoListAtivos() {

        internamentoList = Internamento.list("isAtivo = true ORDER BY id DESC");

        if (internamentoList.isEmpty()) {
            throw new BadRequestException("Internamentos não localizados.");
        }
        return internamentoList;
    }

    public List<Internamento> getInternamentoListInativos() {

        internamentoList = Internamento.list("isAtivo = false ORDER BY id DESC");

        if (internamentoList.isEmpty()) {
            throw new BadRequestException("Internamentos inativos não localizados.");
        }
        return internamentoList;
    }

    public void addInternamento(@NotNull Internamento pInternamento) {

        internamento = Internamento.find("monitoracaoId = ?1 and isAtivo = true ORDER BY id DESC", pInternamento.monitoracao).firstResult();

        if (internamento == null || !internamento.isAtivo) {
            internamento = new Internamento();
            internamento.inicioInternamento = pInternamento.inicioInternamento;
            internamento.fimInternamento = pInternamento.fimInternamento;
            internamento.monitoracao = pInternamento.monitoracao;
            internamento.isObito = pInternamento.isObito;
            internamento.dataAlta = pInternamento.dataAlta;
            internamento.laudoNecroscopico = pInternamento.laudoNecroscopico;
            internamento.isAtivo = true;
            internamento.usuario = pInternamento.usuario;
            internamento.usuarioAcao = "";
            internamento.dataAcao = new Date();

            internamento.persist();

        } else {
            throw new BadRequestException("Internamento já cadastrado!");
        }

    }

    public void updateInternamento(@NotNull Internamento pInternamento) {

        internamento = Internamento.find("monitoracaoId = ?1 and isAtivo = true ORDER BY id DESC", pInternamento.monitoracao).firstResult();


        if (!(internamento == null) && internamento.monitoracao.equals(pInternamento.monitoracao) && internamento.isAtivo) {
            if (!Objects.equals(internamento.inicioInternamento, pInternamento.inicioInternamento)) {
                internamento.inicioInternamento = pInternamento.inicioInternamento;
            }
            if (!internamento.fimInternamento.equals(pInternamento.fimInternamento)) {
                internamento.fimInternamento = pInternamento.fimInternamento;
            }
            if (!internamento.monitoracao.equals(pInternamento.monitoracao)) {
                internamento.monitoracao = pInternamento.monitoracao;
            }
            if (!internamento.isObito.equals(pInternamento.isObito)) {
                internamento.isObito = pInternamento.isObito;
            }
            if (!Objects.equals(internamento.dataAlta, pInternamento.dataAlta)) {
                internamento.dataAlta = pInternamento.dataAlta;
            }
            if (!Objects.equals(internamento.laudoNecroscopico, pInternamento.laudoNecroscopico)) {
                internamento.laudoNecroscopico = pInternamento.laudoNecroscopico;
            }
            if (internamento.usuario.equals(pInternamento.usuario)) {
                internamento.usuario = pInternamento.usuario;
            }
            internamento.dataAcao = new Date();
            internamento.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Internamento.");

        }
    }

    public void deleteInternamento(@NotNull List<Internamento> internamentoList) {

        internamentoList.forEach((pInternamento) -> {
            internamento = Internamento.find("monitoracaoId = ?1 and isAtivo = true ORDER BY id DESC", pInternamento.monitoracao).firstResult();

            if (internamento != null) {
                internamento.isAtivo = Boolean.FALSE;
                internamento.dataAcao = new Date();
                internamento.usuarioAcao = "usuario que deletou";
                internamento.systemDateDeleted = new Date();
                internamento.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar o Internamento.");
            }
        });
    }
}
