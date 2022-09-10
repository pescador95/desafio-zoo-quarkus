package desafio.zoo.controller;

import desafio.zoo.model.Monitoracao;
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
public class MonitoracaoController {

    public Monitoracao monitoracao;
    List<Monitoracao> monitoracaoList = new ArrayList<>();


    public Monitoracao getMonitoracao(@NotNull Monitoracao pMonitoracao) {

        monitoracao = Monitoracao.find("animal = ?1 and dataMonitoracao = ?2 ORDER BY id DESC", pMonitoracao.animal, pMonitoracao.dataMonitoracao).firstResult();

        if (monitoracao == null || !monitoracao.isAtivo) {
            throw new BadRequestException("Monitoração não localizada.");
        }
        return monitoracao;
    }


    public List<Monitoracao> getMonitoracaoListAtivos() {

        monitoracaoList = Monitoracao.list("isAtivo = true ORDER BY id DESC");

        if (monitoracaoList.isEmpty()) {
            throw new BadRequestException("Monitorações não localizados.");
        }
        return monitoracaoList;
    }

    public List<Monitoracao> getMonitoracaoListInativos() {

        monitoracaoList = Monitoracao.list("isAtivo = false ORDER BY id DESC");

        if (monitoracaoList.isEmpty()) {
            throw new BadRequestException("Monitorações inativas não localizadas.");
        }
        return monitoracaoList;
    }

    public void addMonitoracao(@NotNull Monitoracao pMonitoracao) {

        monitoracao = Monitoracao.find("animal = ?1 and dataMonitoracao = ?2 and isAtivo = true ORDER BY id DESC", pMonitoracao.animal, pMonitoracao.dataMonitoracao).firstResult();

        if (monitoracao == null || !monitoracao.isAtivo) {
            monitoracao = new Monitoracao();
            monitoracao.animal = pMonitoracao.animal;
            monitoracao.dataMonitoracao = pMonitoracao.dataMonitoracao;
            monitoracao.temperaturaAnimal = pMonitoracao.temperaturaAnimal;
            monitoracao.etco2 = pMonitoracao.etco2;
            monitoracao.spo2 = pMonitoracao.spo2;
            monitoracao.pd = pMonitoracao.pd;
            monitoracao.pm = pMonitoracao.pm;
            monitoracao.frequenciaRespiratoria = pMonitoracao.frequenciaRespiratoria;
            monitoracao.frequenciaCariaca = pMonitoracao.frequenciaCariaca;
            monitoracao.isAtivo = true;
            monitoracao.usuario = pMonitoracao.usuario;
            monitoracao.usuarioAcao = "";
            monitoracao.dataAcao = new Date();

            monitoracao.persist();

        } else {
            throw new BadRequestException("Monitoração já cadastrada!");
        }

    }

    public void updateMonitoracao(@NotNull Monitoracao pMonitoracao) {

        monitoracao = Monitoracao.find("animal = ?1 and dataMonitoracao = ?2 and isAtivo = true ORDER BY id DESC", pMonitoracao.animal, pMonitoracao.dataMonitoracao).firstResult();


        if (!(monitoracao == null) && monitoracao.animal.equals(pMonitoracao.animal) && monitoracao.isAtivo) {
            if (!monitoracao.dataMonitoracao.equals(pMonitoracao.dataMonitoracao)) {
                monitoracao.dataMonitoracao = pMonitoracao.dataMonitoracao;
            }
            if (!monitoracao.temperaturaAnimal.equals(pMonitoracao.temperaturaAnimal)) {
                monitoracao.temperaturaAnimal = pMonitoracao.temperaturaAnimal;
            }
            if (!monitoracao.etco2.equals(pMonitoracao.etco2)) {
                monitoracao.etco2 = pMonitoracao.etco2;
            }
            if (!monitoracao.spo2.equals(pMonitoracao.spo2)) {
                monitoracao.spo2 = pMonitoracao.spo2;
            }
            if (!monitoracao.pd.equals(pMonitoracao.pd)) {
                monitoracao.pd = pMonitoracao.pd;
            }
            if (!Objects.equals(monitoracao.pm, pMonitoracao.pm)) {
                monitoracao.pm = pMonitoracao.pm;
            }
            if (monitoracao.usuario.equals(pMonitoracao.usuario)) {
                monitoracao.usuario = pMonitoracao.usuario;
            }
            if (monitoracao.frequenciaRespiratoria.equals(pMonitoracao.frequenciaRespiratoria)) {
                monitoracao.frequenciaRespiratoria = pMonitoracao.frequenciaRespiratoria;
            }
            if (monitoracao.frequenciaCariaca.equals(pMonitoracao.frequenciaCariaca)) {
                monitoracao.frequenciaCariaca = pMonitoracao.frequenciaCariaca;
            }
            monitoracao.dataAcao = new Date();
            monitoracao.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar a ficha de monitoração.");

        }
    }

    public void deleteMonitoracao(@NotNull List<Monitoracao> monitoracaoList) {

        monitoracaoList.forEach((pMonitoracao) -> {
            Monitoracao monitoracao = Monitoracao.find("animal = ?1 and dataMonitoracao = ?2 and isAtivo = true ORDER BY id DESC", pMonitoracao.animal, pMonitoracao.dataMonitoracao).firstResult();

            if (monitoracao != null) {
                monitoracao.isAtivo = Boolean.FALSE;
                monitoracao.dataAcao = new Date();
                monitoracao.usuarioAcao = "usuario que deletou";
                monitoracao.systemDateDeleted = new Date();
                monitoracao.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar a ficha de Monitoração.");
            }
        });
    }
}