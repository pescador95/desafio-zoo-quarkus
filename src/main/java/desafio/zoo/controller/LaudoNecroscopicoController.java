package desafio.zoo.controller;

import desafio.zoo.model.LaudoNecroscopico;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class LaudoNecroscopicoController {

    public LaudoNecroscopico laudoNecroscopico;


    public LaudoNecroscopico getLaudoNecroscopico(@NotNull LaudoNecroscopico pLaudoNecroscopico) {

        laudoNecroscopico = LaudoNecroscopico.find("animal = ?1 ORDER BY id DESC", pLaudoNecroscopico.animal).firstResult();

        if (laudoNecroscopico == null || !laudoNecroscopico.isAtivo) {
            throw new BadRequestException("Laudo não localizado.");
        }
        return laudoNecroscopico;
    }

    public void addLaudoNecroscopico(@NotNull LaudoNecroscopico pLaudoNecroscopico) {

        laudoNecroscopico = LaudoNecroscopico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pLaudoNecroscopico.animal).firstResult();

        if (laudoNecroscopico == null || !laudoNecroscopico.isAtivo) {
            laudoNecroscopico = new LaudoNecroscopico();
            laudoNecroscopico.animal = pLaudoNecroscopico.animal;
            laudoNecroscopico.numeroLaudo = pLaudoNecroscopico.numeroLaudo;
            laudoNecroscopico.dataObito = pLaudoNecroscopico.dataObito;
            laudoNecroscopico.dataNecropsia = pLaudoNecroscopico.dataNecropsia;
            laudoNecroscopico.isEutanasia = pLaudoNecroscopico.isEutanasia;
            laudoNecroscopico.isEspontanea = pLaudoNecroscopico.isEspontanea;
            laudoNecroscopico.tipoMorte = pLaudoNecroscopico.tipoMorte;
            laudoNecroscopico.isLaudoEntregue = pLaudoNecroscopico.isLaudoEntregue;
            laudoNecroscopico.isAtivo = true;
            laudoNecroscopico.usuario = pLaudoNecroscopico.usuario;
            laudoNecroscopico.usuarioAcao = "";
            laudoNecroscopico.dataAcao = new Date();

            laudoNecroscopico.persist();

        } else {
            throw new BadRequestException("Laudo já cadastrado!");
        }

    }

    public void updateLaudoNecroscopico(@NotNull LaudoNecroscopico pLaudoNecroscopico) {

        laudoNecroscopico = LaudoNecroscopico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pLaudoNecroscopico.animal).firstResult();


        if (!(laudoNecroscopico == null) && laudoNecroscopico.animal.equals(pLaudoNecroscopico.animal) && laudoNecroscopico.isAtivo) {
            if (laudoNecroscopico.numeroLaudo != pLaudoNecroscopico.numeroLaudo) {
                laudoNecroscopico.numeroLaudo = pLaudoNecroscopico.numeroLaudo;
            }
            if (!laudoNecroscopico.dataObito.equals(pLaudoNecroscopico.dataObito)) {
                laudoNecroscopico.dataObito = pLaudoNecroscopico.dataObito;
            }
            if (!laudoNecroscopico.dataNecropsia.equals(pLaudoNecroscopico.dataNecropsia)) {
                laudoNecroscopico.dataNecropsia = pLaudoNecroscopico.dataNecropsia;
            }
            if (!laudoNecroscopico.isEutanasia.equals(pLaudoNecroscopico.isEutanasia)) {
                laudoNecroscopico.isEutanasia = pLaudoNecroscopico.isEutanasia;
            }
            if (!Objects.equals(laudoNecroscopico.isEspontanea, pLaudoNecroscopico.isEspontanea)) {
                laudoNecroscopico.isEspontanea = pLaudoNecroscopico.isEspontanea;
            }
            if (!Objects.equals(laudoNecroscopico.tipoMorte, pLaudoNecroscopico.tipoMorte)) {
                laudoNecroscopico.tipoMorte = pLaudoNecroscopico.tipoMorte;
            }
            if (!Objects.equals(laudoNecroscopico.isLaudoEntregue, pLaudoNecroscopico.isLaudoEntregue)) {
                laudoNecroscopico.isLaudoEntregue = pLaudoNecroscopico.isLaudoEntregue;
            }
            if (laudoNecroscopico.usuario.equals(pLaudoNecroscopico.usuario)) {
                laudoNecroscopico.usuario = pLaudoNecroscopico.usuario;
            }
            laudoNecroscopico.dataAcao = new Date();
            laudoNecroscopico.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Laudo.");

        }
    }

    public void deleteLaudoNecroscopico(@NotNull LaudoNecroscopico pLaudoNecroscopico) {

        laudoNecroscopico = LaudoNecroscopico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pLaudoNecroscopico.animal).firstResult();

        if (!(laudoNecroscopico == null) && laudoNecroscopico.animal.equals(pLaudoNecroscopico.animal) && (laudoNecroscopico.isAtivo)) {
            laudoNecroscopico.isAtivo = false;
            laudoNecroscopico.usuarioAcao = "usuario que deletou";
            laudoNecroscopico.systemDateDeleted = new Date();
            laudoNecroscopico.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o Laudo.");
        }
    }
}
