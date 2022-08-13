package desafio.zoo.controller;

import desafio.zoo.model.EnriquecimentoAmbiental;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class EnriquecimentoAmbientalController {

    public EnriquecimentoAmbiental enriquecimentoAmbiental;


    public EnriquecimentoAmbiental getEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find("animal = ?1 and dataEnriquecimento = ?2 ORDER BY id DESC", pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento).firstResult();

        if (enriquecimentoAmbiental == null || !enriquecimentoAmbiental.isAtivo) {
            throw new BadRequestException("Enriquecimento Ambiental não localizado.");
        }
        return enriquecimentoAmbiental;
    }

    public void addEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find("animal = ?1 and dataEnriquecimento = ?2 and isAtivo = true ORDER BY id DESC", pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento).firstResult();

        if (enriquecimentoAmbiental == null || !enriquecimentoAmbiental.isAtivo) {
            enriquecimentoAmbiental = new EnriquecimentoAmbiental();
            enriquecimentoAmbiental.animal = pEnriquecimentoAmbiental.animal;
            enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;
            enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;
            enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;
            enriquecimentoAmbiental.dataAcao = pEnriquecimentoAmbiental.dataAcao;
            enriquecimentoAmbiental.systemDateDeleted = pEnriquecimentoAmbiental.systemDateDeleted;
            enriquecimentoAmbiental.isAtivo = true;
            enriquecimentoAmbiental.usuario = pEnriquecimentoAmbiental.usuario;
            enriquecimentoAmbiental.usuarioAcao = "";
            enriquecimentoAmbiental.dataAcao = new Date();

            enriquecimentoAmbiental.persist();

        } else {
            throw new BadRequestException("Enriquecimento Ambiental já cadastrado!");
        }

    }

    public void updateEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find("animal = ?1 and dataEnriquecimento = ?2 and isAtivo = true ORDER BY id DESC", pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento).firstResult();

        if (!(enriquecimentoAmbiental == null) && enriquecimentoAmbiental.animal.equals(pEnriquecimentoAmbiental.animal) && enriquecimentoAmbiental.isAtivo) {
            if (!Objects.equals(enriquecimentoAmbiental.dataEnriquecimento, pEnriquecimentoAmbiental.dataEnriquecimento)) {
                enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;
            }
            if (!enriquecimentoAmbiental.nomeEnriquecimento.equals(pEnriquecimentoAmbiental.nomeEnriquecimento)) {
                enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;
            }
            if (!enriquecimentoAmbiental.descricaoEnriquecimento.equals(pEnriquecimentoAmbiental.descricaoEnriquecimento)) {
                enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;
            }
            if (enriquecimentoAmbiental.usuario.equals(pEnriquecimentoAmbiental.usuario)) {
                enriquecimentoAmbiental.usuario = pEnriquecimentoAmbiental.usuario;
            }
            enriquecimentoAmbiental.dataAcao = new Date();
            enriquecimentoAmbiental.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar o Enriquecimento Ambiental.");

        }
    }

    public void deleteEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find("animal = ?1 and dataEnriquecimento = ?2 and isAtivo = true ORDER BY id DESC", pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento).firstResult();

        if (!(enriquecimentoAmbiental == null) && enriquecimentoAmbiental.animal.equals(pEnriquecimentoAmbiental.animal) && (enriquecimentoAmbiental.isAtivo) && enriquecimentoAmbiental.dataEnriquecimento.equals(pEnriquecimentoAmbiental.dataEnriquecimento)) {
            enriquecimentoAmbiental.isAtivo = false;
            enriquecimentoAmbiental.usuarioAcao = "usuario que deletou";
            enriquecimentoAmbiental.systemDateDeleted = new Date();
            enriquecimentoAmbiental.persist();

        } else {
            throw new BadRequestException("Não foi possível deletar o Enriquecimento Ambiental.");
        }
    }
}
