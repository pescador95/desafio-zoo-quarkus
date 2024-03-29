package desafio.zoo.controller;

import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.Medicacao;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
@Transactional

public class MedicacaoController {

    public Medicacao medicacao;
    public Usuario usuario;
    Responses responses;
    Usuario usuarioAuth;
    HistoricoClinico historicoClinico;

    public Response addMedicacao(@NotNull Medicacao pMedicacao, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();
        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        medicacao = Medicacao.find("historicoClinico = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.historicoClinico).firstResult();
        historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);

        if(historicoClinico != null){
                medicacao = new Medicacao();

                if (pMedicacao.nomeMedicacao != null) {
                    medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
                } else {
                    responses.messages.add("Por favor, preencha o nome da Medicação corretamente!");
                }
                if (pMedicacao.viaAdministracao != null) {
                    medicacao.viaAdministracao = pMedicacao.viaAdministracao;

                } else {
                    responses.messages.add("Por favor, preencha a Via de Administração da Medicação corretamente!");
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

                if (responses.messages.size() < 1) {
                    medicacao.historicoClinico = historicoClinico;
                    medicacao.isAtivo = Boolean.TRUE;
                    medicacao.usuario = usuarioAuth;
                    medicacao.usuarioAcao = usuarioAuth;
                    medicacao.usuarioNome = usuarioAuth.nome;
                    medicacao.usuarioAcaoNome = usuarioAuth.nome;
                    medicacao.dataAcao = new Date();

                    medicacao.persist();
                    responses.status = 201;
                    responses.data = medicacao;
                    responses.messages.add("Medicação Cadastrada com sucesso!");
                } else {
                    return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
                }
                return Response.ok(responses).status(Response.Status.CREATED).build();
        } else {
            responses.status = 500;
            responses.data = pMedicacao;
            responses.messages.add("Por favor, verifique o histórico clínico da medicação.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }

    }

    public Response updateMedicacao(@NotNull Medicacao pMedicacao, String email) {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {

            medicacao = Medicacao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pMedicacao.id).firstResult();
            usuarioAuth = Usuario.find("email = ?1", email).firstResult();

            if (pMedicacao.historicoClinico == null && pMedicacao.nomeMedicacao == null && pMedicacao.posologia == null
                    && pMedicacao.viaAdministracao == null && pMedicacao.frequencia == null) {
                responses.status = 500;
                responses.data = medicacao;
                responses.messages.add("Informe os dados para atualizar a Medicação.");
            } else {
                if (pMedicacao.nomeMedicacao != null && medicacao.nomeMedicacao != null) {
                    if (!medicacao.nomeMedicacao.equals(pMedicacao.nomeMedicacao)) {
                        medicacao.nomeMedicacao = pMedicacao.nomeMedicacao;
                    }
                }
                if (pMedicacao.viaAdministracao != null && medicacao.viaAdministracao != null) {
                    if (!medicacao.viaAdministracao.equals(pMedicacao.viaAdministracao)) {
                        medicacao.viaAdministracao = pMedicacao.viaAdministracao;
                    }
                }
                if (pMedicacao.posologia != null && medicacao.posologia != null) {
                    if (!medicacao.posologia.equals(pMedicacao.posologia)) {
                        medicacao.posologia = pMedicacao.posologia;
                    }
                }
                if (pMedicacao.frequencia != null && medicacao.frequencia != null) {
                    if (!Objects.equals(medicacao.frequencia, pMedicacao.frequencia)) {
                        medicacao.frequencia = pMedicacao.frequencia;
                    }
                }
                if (pMedicacao.historicoClinico != null && medicacao.historicoClinico != null) {
                    if (pMedicacao.historicoClinico != null && medicacao.historicoClinico != null
                            && !medicacao.historicoClinico.equals(pMedicacao.historicoClinico)) {
                        medicacao.historicoClinico = HistoricoClinico.findById(pMedicacao.historicoClinico.id);
                    }
                }
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.usuarioAcaoNome = usuarioAuth.nome;
                medicacao.dataAcao = new Date();
                medicacao.persist();

                responses.status = 200;
                responses.data = medicacao;
                responses.messages.add("Medicação atualizado com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            responses.data = medicacao;
            responses.messages.add("Não foi possível atualizar a medicação.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response deleteMedicacao(List<Long> pListIdMedicacao, String email) {

        List<Medicacao> medicacaoList;
        List<Medicacao> medicacaoListAux = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        medicacaoList = Medicacao.list("id in ?1 and isAtivo = true", pListIdMedicacao);
        int countList = medicacaoList.size();

        try {

            medicacaoList.forEach((medicacao) -> {

                medicacao.isAtivo = Boolean.FALSE;
                medicacao.dataAcao = new Date();
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.usuarioAcaoNome = usuarioAuth.nome;
                medicacao.systemDateDeleted = new Date();
                medicacao.persist();
                medicacaoListAux.add(medicacao);
            });
            responses.status = 200;
            if (countList <= 1) {
                responses.data = medicacao;
                responses.messages.add("Medicação excluída com sucesso.");
            } else {
                responses.dataList = Collections.singletonList(medicacaoListAux);
                responses.messages.add(countList + " Medicações excluídas com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            if (countList <= 1) {
                responses.data = medicacao;
                responses.messages.add("Medicação não localizada ou já excluída.");
            } else {
                responses.dataList = Collections.singletonList(medicacaoList);
                responses.messages.add("Medicações não localizadas ou já excluídas.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response reactivateMedicacao(List<Long> pListIdMedicacao, String email) {

        List<Medicacao> medicacaoList;
        List<Medicacao> medicacaoListAux = new ArrayList<>();
        responses = new Responses();
        responses.messages = new ArrayList<>();

        usuarioAuth = Usuario.find("email = ?1", email).firstResult();
        medicacaoList = Medicacao.list("id in ?1 and isAtivo = false", pListIdMedicacao);
        Integer countList = medicacaoList.size();

        try {

            medicacaoList.forEach((medicacao) -> {

                medicacao.isAtivo = Boolean.TRUE;
                medicacao.dataAcao = new Date();
                medicacao.usuarioAcao = usuarioAuth;
                medicacao.usuarioAcaoNome = usuarioAuth.nome;
                medicacao.systemDateDeleted = null;
                medicacao.persist();
                medicacaoListAux.add(medicacao);
            });
            if (medicacaoListAux.size() <= 1) {
                responses.status = 200;
                responses.data = medicacao;
                responses.messages.add("Medicação reativada com sucesso.");
            } else {
                responses.status = 200;
                responses.dataList = Collections.singletonList(medicacaoListAux);
                responses.messages.add(countList + " Medicações reativadas com sucesso.");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (medicacaoList.size() <= 1) {
                responses.status = 500;
                responses.data = medicacao;
                responses.messages.add("Medicação não localizada ou já reativada.");
            } else {
                responses.status = 500;
                responses.dataList = Collections.singletonList(medicacaoList);
                responses.messages.add("Medicações não localizadas ou já reativadas.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
