package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.EnriquecimentoAmbiental;
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

public class EnriquecimentoAmbientalController {

    public EnriquecimentoAmbiental enriquecimentoAmbiental;

    public void addEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental, String email) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find(
                "animal = ?1 and dataEnriquecimento = ?2 and nomeEnriquecimento = ?3 and descricaoEnriquecimento = ?4 and isAtivo = true ORDER BY id DESC",
                pEnriquecimentoAmbiental.animal, pEnriquecimentoAmbiental.dataEnriquecimento,
                pEnriquecimentoAmbiental.nomeEnriquecimento, pEnriquecimentoAmbiental.descricaoEnriquecimento)
                .firstResult();

        if (enriquecimentoAmbiental == null) {
            enriquecimentoAmbiental = new EnriquecimentoAmbiental();

            if (pEnriquecimentoAmbiental.animal != null) {
                enriquecimentoAmbiental.animal = Animal.findById(pEnriquecimentoAmbiental.animal.id);
            } else {
                throw new BadRequestException("Por favor, preencha o Animal do Enriquecimento Ambiental corretamente!");
            }
            if (pEnriquecimentoAmbiental.dataEnriquecimento != null) {
                enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;

            } else {
                throw new BadRequestException("Por favor, preencha a Data do Enriquecimento Ambiental corretamente!");
            }
            if (pEnriquecimentoAmbiental.nomeEnriquecimento != null) {
                enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;

            } else {
                throw new BadRequestException("Por favor, preencha o Nome do Enriquecimento Ambiental corretamente!");
            }

            if (pEnriquecimentoAmbiental.descricaoEnriquecimento != null) {
                enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;

            } else {
                throw new BadRequestException(
                        "Por favor, preencha a Descrição do Enriquecimento Ambiental corretamente!");
            }

            enriquecimentoAmbiental.isAtivo = Boolean.TRUE;
            enriquecimentoAmbiental.usuario = Usuario.find("email = ?1", email).firstResult();
            enriquecimentoAmbiental.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
            enriquecimentoAmbiental.dataAcao = new Date();

            enriquecimentoAmbiental.persist();

        } else {
            throw new BadRequestException("Enriquecimento Ambiental já cadastrado!");
        }

    }

    public void updateEnriquecimentoAmbiental(@NotNull EnriquecimentoAmbiental pEnriquecimentoAmbiental, String email) {

        enriquecimentoAmbiental = EnriquecimentoAmbiental.find(
                "id = ?1 and dataEnriquecimento = ?2 and isAtivo = true and nomeEnriquecimento = ?3 and descricaoEnriquecimento = ?4 ORDER BY id DESC",
                pEnriquecimentoAmbiental.id, pEnriquecimentoAmbiental.dataEnriquecimento,
                pEnriquecimentoAmbiental.nomeEnriquecimento, pEnriquecimentoAmbiental.descricaoEnriquecimento)
                .firstResult();

        if (enriquecimentoAmbiental != null) {

            if (pEnriquecimentoAmbiental.dataEnriquecimento == null
                    && pEnriquecimentoAmbiental.nomeEnriquecimento == null
                    && pEnriquecimentoAmbiental.descricaoEnriquecimento == null) {
                throw new BadRequestException("Informe os dados para atualizar o Enriquecimento Ambiental.");
            } else {
                if (pEnriquecimentoAmbiental.dataEnriquecimento != null) {
                    if (!Objects.equals(enriquecimentoAmbiental.dataEnriquecimento,
                            pEnriquecimentoAmbiental.dataEnriquecimento)) {
                        enriquecimentoAmbiental.dataEnriquecimento = pEnriquecimentoAmbiental.dataEnriquecimento;
                    }
                }
                if (pEnriquecimentoAmbiental.nomeEnriquecimento != null) {
                    if (!enriquecimentoAmbiental.nomeEnriquecimento
                            .equals(pEnriquecimentoAmbiental.nomeEnriquecimento)) {
                        enriquecimentoAmbiental.nomeEnriquecimento = pEnriquecimentoAmbiental.nomeEnriquecimento;
                    }
                }
                if (pEnriquecimentoAmbiental.descricaoEnriquecimento != null) {
                    if (!enriquecimentoAmbiental.descricaoEnriquecimento
                            .equals(pEnriquecimentoAmbiental.descricaoEnriquecimento)) {
                        enriquecimentoAmbiental.descricaoEnriquecimento = pEnriquecimentoAmbiental.descricaoEnriquecimento;
                    }
                }
                enriquecimentoAmbiental.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Enriquecimento Ambiental.");

        }
    }

    public void deleteEnriquecimentoAmbiental(List<Long> pListEnriquecimentoAmbiental, String email) {

        pListEnriquecimentoAmbiental.forEach((pEnriquecimentoAmbiental) -> {
            enriquecimentoAmbiental = EnriquecimentoAmbiental
                    .find("id = ?1 and isAtivo = true ORDER BY id DESC", pEnriquecimentoAmbiental).firstResult();

            if (enriquecimentoAmbiental != null) {
                enriquecimentoAmbiental.isAtivo = Boolean.FALSE;
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                enriquecimentoAmbiental.systemDateDeleted = new Date();
                enriquecimentoAmbiental.persist();
            } else {
                if (pListEnriquecimentoAmbiental.size() <= 1) {
                    throw new NotFoundException("Enriquecimento Ambiental não localizado ou já excluído.");
                } else {
                    throw new NotFoundException("Enriquecimentos Ambientais não localizados ou já excluídos.");
                }
            }
        });
    }

    public void reactivateEnriquecimentoAmbiental(List<Long> pListEnriquecimentoAmbiental, String email) {

        pListEnriquecimentoAmbiental.forEach((pEnriquecimentoAmbiental) -> {
            enriquecimentoAmbiental = EnriquecimentoAmbiental
                    .find("id = ?1 and isAtivo = false ORDER BY id DESC", pEnriquecimentoAmbiental).firstResult();

            if (enriquecimentoAmbiental != null) {
                enriquecimentoAmbiental.isAtivo = Boolean.TRUE;
                enriquecimentoAmbiental.dataAcao = new Date();
                enriquecimentoAmbiental.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                enriquecimentoAmbiental.systemDateDeleted = null;
                enriquecimentoAmbiental.persist();
            } else {
                if (pListEnriquecimentoAmbiental.size() <= 1) {
                    throw new NotFoundException("Enriquecimento Ambiental não localizados ou já reativado.");
                } else {
                    throw new NotFoundException("Enriquecimentos Ambientais não localizados ou já reativados.");
                }
            }
        });
    }
}