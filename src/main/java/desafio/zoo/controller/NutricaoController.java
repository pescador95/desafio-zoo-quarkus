package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Nutricao;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional
public class NutricaoController {

    public Nutricao nutricao;

    public void addNutricao(@NotNull Nutricao pNutricao, String email) {

        nutricao = Nutricao.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.animal).firstResult();
        if (nutricao == null) {
            nutricao = new Nutricao();

            if (pNutricao.descricaoNutricao != null) {
                nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            } else {
                throw new BadRequestException("Por favor, preencha a descrição da Nutrição corretamente!");
            }

            nutricao.isAtivo = Boolean.TRUE;

            if (pNutricao.dataInicio != null) {
                nutricao.dataInicio = pNutricao.dataInicio;
            } else {
                throw new BadRequestException("Por favor, preencha a data de Início da Nutrição corretamente!");
            }

            if (pNutricao.dataFim != null) {
                nutricao.dataFim = pNutricao.dataFim;

            } else {
                throw new BadRequestException("Por favor, preencha a data fim da Nutrição corretamente!");
            }
            if (pNutricao.animal != null) {
                nutricao.animal = Animal.findById(pNutricao.animal.id);
            } else {
                throw new BadRequestException("Por favor, preencha o Animal da Nutrição corretamente!");
            }

            nutricao.usuario = Usuario.find("email = ?1", email).firstResult();
            nutricao.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
            nutricao.dataAcao = new Date();

            nutricao.persist();

        } else {
            throw new BadRequestException("Nutrição já cadastrado!");
        }

    }

    public void updateNutricao(@NotNull Nutricao pNutricao, String email) {

        nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();

        if (nutricao != null) {
            if (pNutricao.dataInicio == null && pNutricao.dataFim == null && pNutricao.descricaoNutricao == null
                    && pNutricao.animal == null) {
                throw new BadRequestException("Informe os dados para atualizar a Nutrição.");
            } else {
                if (pNutricao.dataInicio != null) {
                    if (!nutricao.dataInicio.equals(pNutricao.dataInicio)) {
                        nutricao.dataInicio = pNutricao.dataInicio;
                    }
                }
                if (pNutricao.dataFim != null) {
                    if (!nutricao.dataFim.equals(pNutricao.dataFim)) {
                        nutricao.dataFim = pNutricao.dataFim;
                    }
                }
                if (pNutricao.descricaoNutricao != null) {
                    if (!nutricao.descricaoNutricao.equals(pNutricao.descricaoNutricao)) {
                        nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
                    }
                }
                if (pNutricao.animal != null) {
                    if (nutricao.animal != null && !nutricao.animal.equals(pNutricao.animal)) {
                        nutricao.animal = Animal.findById(pNutricao.animal.id);
                    }
                }

                nutricao.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                nutricao.dataAcao = new Date();
                nutricao.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar a ficha de Nutrição.");

        }
    }

    public void deleteNutricao(List<Long> pListIdnutricao, String email) {

        pListIdnutricao.forEach((pNutricao) -> {
            nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao).firstResult();

            if (nutricao != null) {
                nutricao.isAtivo = Boolean.FALSE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                nutricao.systemDateDeleted = new Date();
                nutricao.persist();
            } else {
                if (pListIdnutricao.size() <= 1) {
                    throw new NotFoundException("Ficha de Nutrição não localizada ou já excluída.");
                } else {
                    throw new NotFoundException("Fichas de Nutrição não localizadas ou já excluídas.");
                }
            }
        });
    }

    public void reactivateNutricao(List<Long> nutricaoList, String email) {

        nutricaoList.forEach((pNutricao) -> {
            nutricao = Nutricao.find("id = ?1 and isAtivo = false ORDER BY id DESC", pNutricao).firstResult();

            if (nutricao != null) {
                nutricao.isAtivo = Boolean.TRUE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                nutricao.systemDateDeleted = null;
                nutricao.persist();
            } else {
                if (nutricaoList.size() <= 1) {
                    throw new NotFoundException("Ficha de Nutrição não localizada ou já reativada.");
                } else {
                    throw new NotFoundException("Fichas de Nutrição não localizadas ou já reativadas.");
                }
            }
        });
    }
}