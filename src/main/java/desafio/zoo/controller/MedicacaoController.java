package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.Medicacao;
import desafio.zoo.model.Usuario;
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

public class MedicacaoController {

    public Medicacao medicacao;
    public Usuario usuario;
    List<Medicacao> medicacaoList = new ArrayList<>();


    public Medicacao getMedicacao(@NotNull Medicacao pMedicacao) {

        medicacao = Medicacao.find("id = ?1 ORDER BY id DESC", pMedicacao.id).firstResult();

        if (medicacao == null || !medicacao.isAtivo) {
            throw new BadRequestException("Medicação não localizada.");
        }
        return medicacao;
    }

    public List<Medicacao> getMedicacaoListAtivos() {

        medicacaoList = Medicacao.list("isAtivo = true ORDER BY id DESC");

        if (medicacaoList.isEmpty()) {
            throw new BadRequestException("Medicações não localizadas.");
        }
        return medicacaoList;
    }

    public List<Medicacao> getMedicacaoListInativos() {

        medicacaoList = Medicacao.list("isAtivo = false ORDER BY id DESC");

        if (medicacaoList.isEmpty()) {
            throw new BadRequestException("Medicações inativas não localizadas.");
        }
        return medicacaoList;
    }

    public void addMedicacao(@NotNull Medicacao pMedicacao) {

        medicacao = Medicacao.find("historicoClinico = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.historicoClinico).firstResult();

        if (medicacao == null || !medicacao.isAtivo) {
            medicacao = new Medicacao();
            medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
            medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
            medicacao.viaAdministracao = pMedicacao.viaAdministracao;
            medicacao.posologia = pMedicacao.posologia;
            medicacao.frequencia = pMedicacao.frequencia;
            medicacao.isAtivo = true;
            medicacao.usuario = Usuario.findById(pMedicacao.usuario.id);
            medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
            medicacao.dataAcao = new Date();

            medicacao.persist();

        } else {
            throw new BadRequestException("medicação já cadastrada!");
        }

    }

    public void updateMedicacao(@NotNull Medicacao pMedicacao) {

        medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();


        if (!(medicacao == null) && medicacao.id.equals(pMedicacao.id) && medicacao.isAtivo) {
            if (!medicacao.nomeMedicacao.equals(pMedicacao.nomeMedicacao)) {
                medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
            }
            if (!medicacao.viaAdministracao.equals(pMedicacao.viaAdministracao)) {
                medicacao.viaAdministracao = pMedicacao.viaAdministracao;
            }
            if (!medicacao.posologia.equals(pMedicacao.posologia)) {
                medicacao.posologia = pMedicacao.posologia;
            }
            if (!Objects.equals(medicacao.frequencia, pMedicacao.frequencia)) {
                medicacao.frequencia = pMedicacao.frequencia;
            }
            if (!medicacao.historicoClinico.equals(pMedicacao.historicoClinico)) {
                medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
            }
            medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
            medicacao.dataAcao = new Date();
            medicacao.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar a medicação.");

        }
    }

    public void deleteMedicacao(@NotNull List<Medicacao> medicacaoList) {

        medicacaoList.forEach((pMedicacao) -> {
            medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();

            if (medicacao != null) {
                medicacao.isAtivo = Boolean.FALSE;
                medicacao.dataAcao = new Date();
                medicacao.usuarioAcao = Usuario.findById(pMedicacao.usuarioAcao.id);
                medicacao.systemDateDeleted = new Date();
                medicacao.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar a medicação.");
            }
        });
    }
}