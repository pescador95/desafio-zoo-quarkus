package main.java.org.acme.controller;

import main.java.org.acme.model.Nutricao;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
@Transactional
public class NutricaoController {
    public String mensagem;
    public Nutricao nutricao;


    public Nutricao getNutricao(@NotNull Nutricao pNutricao) {
        nutricao = Nutricao.find("animal", pNutricao.animal).firstResult();

        if (nutricao == null) {
            mensagem = ("Nutrição não foi localizado.");
            throw new BadRequestException("Nutrição não foi localizado.");
        }
    return nutricao;
    }

    public void addNutricao(@NotNull Nutricao pNutricao) {
        nutricao = Nutricao.find("animal", pNutricao.animal).firstResult();

        if (nutricao == null) {
            nutricao = new Nutricao();
            nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            nutricao.alimento = pNutricao.alimento;
            nutricao.isAtivo = pNutricao.isAtivo;
            nutricao.dataInicio = pNutricao.dataInicio;
            nutricao.dataFim = pNutricao.dataFim;
            nutricao.quantidade = pNutricao.quantidade;
            nutricao.valorUnidadeMedida = pNutricao.valorUnidadeMedida;
            nutricao.usuario = pNutricao.usuario;
            nutricao.animal = pNutricao.animal;


            nutricao.persist();
            mensagem = "Nutrição criado com sucesso!";

        } else {
            mensagem = ("Nutrição já cadastrado!");
            throw new BadRequestException("Nutrição já cadastrado!");
        }

    }

    public void updateNutricao(@NotNull Nutricao pNutricao) {
        nutricao = Nutricao.find("animal", pNutricao.animal).firstResult();


        if (!(nutricao == null) && nutricao.animal.equals(pNutricao.animal)) {

            if (!nutricao.dataInicio.equals(pNutricao.dataInicio)) {
                nutricao.dataInicio = pNutricao.dataInicio;
            }
            if (!nutricao.dataFim.equals(pNutricao.dataFim)) {
                nutricao.dataFim = pNutricao.dataFim;
            }
            if (!nutricao.descricaoNutricao.equals(pNutricao.descricaoNutricao)) {
                nutricao.descricaoNutricao = pNutricao.descricaoNutricao;
            }
            if (!nutricao.alimento.equals(pNutricao.alimento)) {
                nutricao.alimento = pNutricao.alimento;
            }
            if (!nutricao.quantidade.equals(pNutricao.quantidade)) {
                nutricao.quantidade = pNutricao.quantidade;
            }
            if (nutricao.valorUnidadeMedida != pNutricao.valorUnidadeMedida) {
                nutricao.valorUnidadeMedida = pNutricao.valorUnidadeMedida;
            }
            if (nutricao.usuario.equals(pNutricao.usuario)) {
                nutricao.usuario = pNutricao.usuario;
            }
            nutricao.persist();
            mensagem = "Nutrição atualizada com sucesso!";
        } else {
            mensagem = ("Não foi possível atualizar a ficha de Nutrição.");
            throw new BadRequestException("Não foi possível atualizar a ficha de Nutrição.");

        }
    }

    public void deleteNutricao(@NotNull Nutricao pNutricao) {
        nutricao = Nutricao.find("animal", pNutricao.animal).firstResult();


            if (!(nutricao == null) && nutricao.animal.equals(pNutricao.animal)) {
                nutricao.delete();
                mensagem = "Nutrição deletado com sucesso!";
            } else {
                mensagem = ("Não foi possível deletar a ficha de Nutrição.");
                throw new BadRequestException("Não foi possível deletar a ficha de Nutrição.");
            }
    }
}
