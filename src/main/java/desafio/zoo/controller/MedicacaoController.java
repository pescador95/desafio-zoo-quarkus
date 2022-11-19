package desafio.zoo.controller;

import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.Medicacao;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional

public class MedicacaoController {

    public Medicacao medicacao;
    public Usuario usuario;
    Responses responses;
    Usuario usuarioAuth;

    public void addMedicacao(@NotNull Medicacao pMedicacao, String email) {

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        medicacao = Medicacao
                .find("historicoClinico = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.historicoClinico)
                .firstResult();

        if (medicacao == null) {
            medicacao = new Medicacao();

            if (pMedicacao.historicoClinico != null) {
                medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
            }
            if (pMedicacao.nomeMedicacao != null) {
                medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
            } else {
                responses.messages.add("Por favor, preencha o nome da Medicação corretamente!");
            }
            if (pMedicacao.viaAdministracao != null) {
                medicacao.viaAdministracao = pMedicacao.viaAdministracao;

            } else {
                responses.messages = Arrays
                        .asList("Por favor, preencha a Via de Administração da Medicação corretamente!");
            }
            if (pMedicacao.posologia != null) {
                medicacao.posologia = pMedicacao.posologia;

            } else {
                responses.messages.add("Por favor, preencha a posologia da Medicação corretamente!");
            }
            if (pMedicacao.frequencia != null) {
                medicacao.frequencia = pMedicacao.frequencia;
            } else {
                responses.messages.add("Por favor, preencha a Frequência da Medicação corretamente!");
            }
            medicacao.isAtivo = Boolean.TRUE;
            medicacao.usuario = usuarioAuth;
            medicacao.usuarioAcao = usuarioAuth;
            medicacao.dataAcao = new Date();

            medicacao.persist();

        } else {
            responses.messages.add("medicação já cadastrada!");
        }

    }

    public void updateMedicacao(@NotNull Medicacao pMedicacao, String email) {

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();

        if (medicacao != null) {
            if (pMedicacao.historicoClinico == null && pMedicacao.nomeMedicacao == null && pMedicacao.posologia == null
                    && pMedicacao.viaAdministracao == null && pMedicacao.frequencia == null) {
                responses.messages.add("Informe os dados para atualizar a Medicação.");
            } else {
                if (pMedicacao.nomeMedicacao != null) {
                    if (!medicacao.nomeMedicacao.equals(pMedicacao.nomeMedicacao)) {
                        medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
                    }
                }
                if (pMedicacao.viaAdministracao != null) {
                    if (!medicacao.viaAdministracao.equals(pMedicacao.viaAdministracao)) {
                        medicacao.viaAdministracao = pMedicacao.viaAdministracao;
                    }
                }
                if (!medicacao.posologia.equals(pMedicacao.posologia)) {
                    medicacao.posologia = pMedicacao.posologia;
                }
                if (pMedicacao.frequencia != null) {
                    if (!Objects.equals(medicacao.frequencia, pMedicacao.frequencia)) {
                        medicacao.frequencia = pMedicacao.frequencia;
                    }
                }
                if (pMedicacao.historicoClinico != null) {
                    if (pMedicacao.historicoClinico != null && medicacao.historicoClinico != null
                            && !medicacao.historicoClinico.equals(pMedicacao.historicoClinico)) {
                        medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
                    }
                }
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.dataAcao = new Date();
                medicacao.persist();
            }
        } else {
            responses.messages.add("Não foi possível atualizar a medicação.");

        }
    }

    public void deleteMedicacao(List<Long> pListMedicacao, String email) {

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        pListMedicacao.forEach((pMedicacao) -> {
            medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao).firstResult();

            if (medicacao != null) {
                medicacao.isAtivo = Boolean.FALSE;
                medicacao.dataAcao = new Date();
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.systemDateDeleted = new Date();
                medicacao.persist();
            } else {
                if (pListMedicacao.size() <= 1) {
                    throw new NotFoundException("Medicação não localizada ou já excluída.");
                } else {
                    throw new NotFoundException("Medicações não localizadas ou já excluídas.");
                }
            }
        });
    }

    public void reactivateMedicacao(List<Long> pListMedicacao, String email) {

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();

        pListMedicacao.forEach((pMedicacao) -> {
            medicacao = Medicacao.find("id = ?1 and isAtivo = false ORDER BY id DESC", pMedicacao).firstResult();

            if (medicacao != null) {
                medicacao.isAtivo = Boolean.TRUE;
                medicacao.dataAcao = new Date();
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.systemDateDeleted = null;
                medicacao.persist();
            } else {
                if (pListMedicacao.size() <= 1) {
                    throw new NotFoundException("Medicação não localizada ou já reativada.");
                } else {
                    throw new NotFoundException("Medicações não localizadas ou já reativadas.");
                }
            }
        });
    }

}
