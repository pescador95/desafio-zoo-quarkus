package desafio.zoo.controller;

import desafio.zoo.model.AdministracaoMedicacao;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional

public class AdministracaoMedicacaoController {

    public AdministracaoMedicacao administracaoMedicacao;
    List<AdministracaoMedicacao> administracaoMedicacaoList = new ArrayList<>();



    public AdministracaoMedicacao getAdministracaoMedicacao(@NotNull AdministracaoMedicacao pAdministracaoMedicacao) {

        administracaoMedicacao = AdministracaoMedicacao.find("medicacao = ?1 ORDER BY id DESC", pAdministracaoMedicacao.medicacao).firstResult();

        if (administracaoMedicacao == null || !administracaoMedicacao.isAtivo) {
            throw new BadRequestException("Administração de Medicação não localizada.");
        }
        return administracaoMedicacao;
    }

    public List<AdministracaoMedicacao> getAdministracaoMedicacaoListAtivos() {

        administracaoMedicacaoList = AdministracaoMedicacao.list("isAtivo = true ORDER BY id DESC");

        if (administracaoMedicacaoList.isEmpty()) {
            throw new BadRequestException("Medicações Administradas não localizadas ou inativas.");
        }
        return administracaoMedicacaoList;
    }

    public List<AdministracaoMedicacao> getAdministracaoMedicacaoListInativos() {

        administracaoMedicacaoList = AdministracaoMedicacao.list("isAtivo = false ORDER BY id DESC");

        if (administracaoMedicacaoList.isEmpty()) {
            throw new BadRequestException("Medicações Administradas inativas não localizadas.");
        }
        return administracaoMedicacaoList;
    }

    public void addAdministracaoMedicacao(@NotNull AdministracaoMedicacao pAdministracaoMedicacao) {

        administracaoMedicacao = AdministracaoMedicacao.find("medicacao = ?1 and isAtivo = true ORDER BY id DESC", pAdministracaoMedicacao.medicacao).firstResult();

        if (administracaoMedicacao == null || !administracaoMedicacao.isAtivo) {
            administracaoMedicacao = new AdministracaoMedicacao();
            administracaoMedicacao.dataMedicacao = pAdministracaoMedicacao.dataMedicacao;
            administracaoMedicacao.usuario = pAdministracaoMedicacao.usuario;
            administracaoMedicacao.medicacao = pAdministracaoMedicacao.medicacao;
            administracaoMedicacao.isAtivo = true;
            administracaoMedicacao.usuarioAcao = "";
            administracaoMedicacao.dataAcao = new Date();

            administracaoMedicacao.persist();

        } else {
            throw new BadRequestException("administracaoMedicacao já cadastrada!");
        }

    }

    public void updateAdministracaoMedicacao(@NotNull AdministracaoMedicacao pAdministracaoMedicacao) {

        administracaoMedicacao = AdministracaoMedicacao.find("medicacao = ?1 and dataMedicacao = ?2 and isAtivo = true ORDER BY id DESC", pAdministracaoMedicacao.medicacao, pAdministracaoMedicacao.dataMedicacao).firstResult();

        if (!(administracaoMedicacao == null) && administracaoMedicacao.medicacao.equals(pAdministracaoMedicacao.medicacao) && administracaoMedicacao.isAtivo && administracaoMedicacao.dataMedicacao.equals(pAdministracaoMedicacao.dataMedicacao)) {
            if (!administracaoMedicacao.dataMedicacao.equals(pAdministracaoMedicacao.dataMedicacao)) {
                administracaoMedicacao.dataMedicacao = pAdministracaoMedicacao.dataMedicacao;
            }
            if (!administracaoMedicacao.usuario.equals(pAdministracaoMedicacao.usuario)) {
                administracaoMedicacao.usuario = pAdministracaoMedicacao.usuario;
            }
            administracaoMedicacao.dataAcao = new Date();
            administracaoMedicacao.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar a Administração de Medicação.");

        }
    }

    public void deleteAdministracaoMedicacao(@NotNull List<AdministracaoMedicacao> administracaoMedicacaoList) {

        administracaoMedicacaoList.forEach((pAdministracaoMedicacao) -> {
            administracaoMedicacao = AdministracaoMedicacao.find("medicacao = ?1 and dataMedicacao = ?2 and isAtivo = true ORDER BY id DESC", pAdministracaoMedicacao.medicacao, pAdministracaoMedicacao.dataMedicacao).firstResult();

            if (administracaoMedicacao != null) {
                administracaoMedicacao.isAtivo = Boolean.FALSE;
                administracaoMedicacao.dataAcao = new Date();
                administracaoMedicacao.usuarioAcao = "usuario que deletou";
                administracaoMedicacao.systemDateDeleted = new Date();
                administracaoMedicacao.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar a administração Medicação.");
            }
        });
    }
}