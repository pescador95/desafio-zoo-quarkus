package desafio.zoo.controller;

import desafio.zoo.model.Nutricao;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
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
            throw new BadRequestException("Nutrição não foi localizado.");
        }
    return nutricao;
    }

    public List<Nutricao> getNutricaoListAtivos() {

        nutricaoList = Nutricao.list("isAtivo = true ORDER BY id DESC");

        if (nutricaoList.isEmpty()) {
            throw new BadRequestException("Lista de Nutrições não localizadas.");
        }
        return nutricaoList;
    }

    public List<Nutricao> getNutricaoListInativos() {

        nutricaoList = Nutricao.list("isAtivo = false ORDER BY id DESC");

        if (nutricaoList.isEmpty()) {
            throw new BadRequestException("Lista de Nutrições inativas não localizadas.");
        }
        return nutricaoList;
    }

    public void addNutricao(@NotNull Nutricao pNutricao) {

        nutricao = Nutricao.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.animal).firstResult();

        if (nutricao == null || !nutricao.isAtivo) {
            nutricao = new Nutricao();
            nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            nutricao.isAtivo = true;
            nutricao.dataInicio = pNutricao.dataInicio;
            nutricao.dataFim = pNutricao.dataFim;
            nutricao.usuario = pNutricao.usuario;
            nutricao.animal = pNutricao.animal;
            nutricao.usuarioAcao = "";
            nutricao.dataAcao = new Date();

            nutricao.persist();

        } else {
            throw new BadRequestException("Nutrição já cadastrado!");
        }

    }

    public void updateNutricao(@NotNull Nutricao pNutricao) {

        nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();


        if (!(nutricao == null) && nutricao.id.equals(pNutricao.id) && nutricao.isAtivo) {
            if (!nutricao.dataInicio.equals(pNutricao.dataInicio)) {
                nutricao.dataInicio = pNutricao.dataInicio;
            }
            if (!nutricao.dataFim.equals(pNutricao.dataFim)) {
                nutricao.dataFim = pNutricao.dataFim;
            }
            if (!nutricao.descricaoNutricao.equals(pNutricao.descricaoNutricao)) {
                nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            }
            if (nutricao.usuario.equals(pNutricao.usuario)) {
                nutricao.usuario = pNutricao.usuario;
            }
            nutricao.dataAcao = new Date();
            nutricao.persist();

        } else {
            throw new BadRequestException("Não foi possível atualizar a ficha de Nutrição.");

        }
    }

    public void deleteNutricao(@NotNull List<Nutricao> nutricaoList) {

        nutricaoList.forEach((pNutricao) -> {
            Nutricao nutricao = Nutricao.find("id = ?1 and isAtivo = true ORDER BY id DESC", pNutricao.id).firstResult();

            if (nutricao != null) {
                nutricao.isAtivo = Boolean.FALSE;
                nutricao.dataAcao = new Date();
                nutricao.usuarioAcao = "usuario que deletou";
                nutricao.systemDateDeleted = new Date();
                nutricao.persist();
            } else {
                throw new BadRequestException("Não foi possível deletar a ficha de Nutrição.");
            }
        });
    }
}