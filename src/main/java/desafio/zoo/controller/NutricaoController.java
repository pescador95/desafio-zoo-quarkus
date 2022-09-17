package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Nutricao;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional
public class NutricaoController {

    public Nutricao nutricao;
    List<Nutricao> nutricaoList = new ArrayList<>();


    public Nutricao getNutricao(@NotNull Nutricao pNutricao) {

        nutricao = Nutricao.find("id = ?1 ORDER BY id DESC", pNutricao.id).firstResult();

        if (nutricao == null || !nutricao.isAtivo) {
            throw new NotFoundException("Nutrição não foi localizado.");//TODO organizar mensagem
        }
        return nutricao;
    }

    public List<Nutricao> getNutricaoListAtivos() {

        nutricaoList = Nutricao.list("isAtivo = true ORDER BY id DESC");

        if (nutricaoList.isEmpty()) {
            throw new NotFoundException("Lista de Nutrições não localizadas.");//TODO organizar mensagem
        }
        return nutricaoList;
    }

    public List<Nutricao> getNutricaoListInativos() {

        nutricaoList = Nutricao.list("isAtivo = false ORDER BY id DESC");

        if (nutricaoList.isEmpty()) {
            throw new NotFoundException("Lista de Nutrições inativas não localizadas.");//TODO organizar mensagem
        }
        return nutricaoList;
    }

    public void addNutricao(@NotNull Nutricao pNutricao) {

        nutricao = Nutricao.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.animal).firstResult();
        //TODO verificar quais props são obrigatórias na criação.
        if (nutricao == null) {
            nutricao = new Nutricao();

            if (pNutricao.descricaoNutricao != null) {
                nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            } else {
                throw new BadRequestException("Por favor, preencha a descrição da Nutrição corretamente!");//TODO organizar mensagem
            }

            nutricao.isAtivo = Boolean.TRUE;

            if (pNutricao.dataInicio != null) {
                nutricao.dataInicio = pNutricao.dataInicio;
            } else {
                throw new BadRequestException("Por favor, preencha a data de Início da Nutrição corretamente!");//TODO organizar mensagem
            }

            if (pNutricao.dataFim != null) {
                nutricao.dataFim = pNutricao.dataFim;

            } else {
                throw new BadRequestException("Por favor, preencha a data fim da Nutrição corretamente!");//TODO organizar mensagem
            }
            if (pNutricao.animal != null) {
                nutricao.animal = Animal.findById(pNutricao.animal.id);
            } else {
                throw new BadRequestException("Por favor, preencha o Animal da Nutrição corretamente!");//TODO organizar mensagem
            }

            nutricao.usuario = Usuario.findById(pNutricao.usuario.id);
            nutricao.usuarioAcao = Usuario.findById(pNutricao.usuarioAcao.id);
            nutricao.dataAcao = new Date();

            nutricao.persist();

        } else {
            throw new BadRequestException("Nutrição já cadastrado!");//TODO organizar mensagem
        }

    }

    public void updateNutricao(@NotNull Nutricao pNutricao) {

        nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();

        if (nutricao != null) {
            if (pNutricao.dataInicio == null && pNutricao.dataFim == null && pNutricao.descricaoNutricao == null && pNutricao.animal == null) {
                throw new BadRequestException("Informe os dados para atualizar a Nutrição.");//TODO organizar mensagem
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

                nutricao.usuarioAcao = Usuario.findById(pNutricao.usuarioAcao.id);
                nutricao.dataAcao = new Date();
                nutricao.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar a ficha de Nutrição.");//TODO organizar mensagem

        }
    }

    public void deleteNutricao(@NotNull List<Nutricao> nutricaoList) {

        nutricaoList.forEach((pNutricao) -> {
            nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();

            if (nutricao != null) {
                nutricao.isAtivo = Boolean.FALSE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = Usuario.findById(pNutricao.usuarioAcao.id);
                nutricao.systemDateDeleted = new Date();
                nutricao.persist();
            } else {
                if (nutricaoList.size() <= 1) {
                    throw new NotFoundException("Ficha de Nutrição não localizada ou já excluída.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Fichas de Nutrição não localizadas ou já excluídas.");//TODO organizar mensagem
                }
            }
        });
    }

    public void reactivateNutricao(@NotNull List<Nutricao> nutricaoList) {

        nutricaoList.forEach((pNutricao) -> {
            nutricao = Nutricao.find("id = ?1 and isAtivo = false ORDER BY id DESC", pNutricao.id).firstResult();

            if (nutricao != null) {
                nutricao.isAtivo = Boolean.TRUE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = Usuario.findById(pNutricao.usuarioAcao.id);
                nutricao.systemDateDeleted = null;
                nutricao.persist();
            } else {
                if (nutricaoList.size() <= 1) {
                    throw new NotFoundException("Ficha de Nutrição não localizada ou já reativada.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Fichas de Nutrição não localizadas ou já reativadas.");//TODO organizar mensagem
                }
            }
        });
    }
}