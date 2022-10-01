package desafio.zoo.controller;

import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.Medicacao;
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

public class MedicacaoController {

    public Medicacao medicacao;
    public Usuario usuario;
    List<Medicacao> medicacaoList = new ArrayList<>();

    public List<Medicacao> getMedicacaoListAtivos() {

        medicacaoList = Medicacao.list("isAtivo = true ORDER BY id DESC");//TODO organizar mensagem

        if (medicacaoList.isEmpty()) {
            throw new NotFoundException("Medicações não localizadas.");//TODO organizar mensagem
        }
        return medicacaoList;
    }

    public List<Medicacao> getMedicacaoListInativos() {

        medicacaoList = Medicacao.list("isAtivo = false ORDER BY id DESC");//TODO organizar mensagem

        if (medicacaoList.isEmpty()) {
            throw new NotFoundException("Medicações inativas não localizadas.");//TODO organizar mensagem
        }
        return medicacaoList;
    }

    public void addMedicacao(@NotNull Medicacao pMedicacao) {

        medicacao = Medicacao.find("historicoClinico = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.historicoClinico).firstResult();
//TODO verificar quais props são obrigatórias na criação.
        if (medicacao == null) {
            medicacao = new Medicacao();

            if (pMedicacao.historicoClinico != null) {
                medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
            }
            if (pMedicacao.nomeMedicacao != null) {
                medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
            } else {
                throw new BadRequestException("Por favor, preencha o nome da Medicação corretamente!");//TODO organizar mensagem
            }
            if (pMedicacao.viaAdministracao != null) {
                medicacao.viaAdministracao = pMedicacao.viaAdministracao;

            } else {
                throw new BadRequestException("Por favor, preencha a Via de Administração da Medicação corretamente!");//TODO organizar mensagem
            }
            if (pMedicacao.posologia != null) {
                medicacao.posologia = pMedicacao.posologia;

            } else {
                throw new BadRequestException("Por favor, preencha a posologia da Medicação corretamente!");//TODO organizar mensagem
            }
            if (pMedicacao.frequencia != null) {
                medicacao.frequencia = pMedicacao.frequencia;
            } else {
                throw new BadRequestException("Por favor, preencha a Frequência da Medicação corretamente!");//TODO organizar mensagem
            }
            medicacao.isAtivo = Boolean.TRUE;
            medicacao.usuario = Usuario.findById(pMedicacao.usuario.id);
            medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
            medicacao.dataAcao = new Date();

            medicacao.persist();

        } else {
            throw new BadRequestException("medicação já cadastrada!");//TODO organizar mensagem
        }

    }

    public void updateMedicacao(@NotNull Medicacao pMedicacao) {

        medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();


        if (medicacao != null) {
            if (pMedicacao.historicoClinico == null && pMedicacao.nomeMedicacao == null && pMedicacao.posologia == null && pMedicacao.viaAdministracao == null && pMedicacao.frequencia == null) {
                throw new BadRequestException("Informe os dados para atualizar a Medicação.");//TODO organizar mensagem
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
                    if (pMedicacao.historicoClinico != null && medicacao.historicoClinico != null && !medicacao.historicoClinico.equals(pMedicacao.historicoClinico)) {
                        medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
                    }
                }
                medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
                medicacao.dataAcao = new Date();
                medicacao.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar a medicação.");//TODO organizar mensagem

        }
    }

    public void deleteMedicacao(@NotNull List<Medicacao> medicacaoList) {

        medicacaoList.forEach((pMedicacao) -> {
            medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();

            if (medicacao != null) {
                medicacao.isAtivo = Boolean.FALSE;
                medicacao.dataAcao = new Date();
                if (pMedicacao.usuarioAcao != null) {
                    medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
                }
                medicacao.systemDateDeleted = new Date();
                medicacao.persist();
            } else {
                if (medicacaoList.size() <= 1) {
                    throw new NotFoundException("Medicação não localizada ou já excluída.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Medicações não localizadas ou já excluídas.");//TODO organizar mensagem
                }
            }
        });
    }

    public void reactivateMedicacao(@NotNull List<Medicacao> medicacaoList) {

        medicacaoList.forEach((pMedicacao) -> {
            medicacao = Medicacao.find("id = ?1 and isAtivo = false ORDER BY id DESC", pMedicacao.id).firstResult();

            if (medicacao != null) {
                medicacao.isAtivo = Boolean.TRUE;
                medicacao.dataAcao = new Date();
                if (pMedicacao.usuarioAcao != null) {
                    medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
                }
                medicacao.systemDateDeleted = null;
                medicacao.persist();
            } else {
                if (medicacaoList.size() <= 1) {
                    throw new NotFoundException("Medicação não localizada ou já reativada.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Medicações não localizadas ou já reativadas.");//TODO organizar mensagem
                }
            }
        });
    }

}
