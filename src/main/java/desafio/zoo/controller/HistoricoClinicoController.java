package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.HistoricoClinico;
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

public class HistoricoClinicoController {

    public HistoricoClinico historicoClinico;
    public void addHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico, String email) {

        historicoClinico = HistoricoClinico.find("animal = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.animal).firstResult();

        if (historicoClinico == null) {
            historicoClinico = new HistoricoClinico();

            if (pHistoricoClinico.animal != null) {
                historicoClinico.animal = Animal.findById(pHistoricoClinico.animal.id);
            } else {
                throw new BadRequestException("Por favor, informar o Animal do Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.etco2 != null) {
                historicoClinico.etco2 = pHistoricoClinico.etco2;
            } else {
                throw new BadRequestException("Por favor, informar o etco2 do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.spo2 != null) {
                historicoClinico.spo2 = pHistoricoClinico.spo2;
            } else {
                throw new BadRequestException("Por favor, informar o spo2 do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.temperaturaAnimal != null) {
                historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
            } else {
                throw new BadRequestException("Por favor, informar a temperatura do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.ps != null) {
                historicoClinico.ps = pHistoricoClinico.ps;
            } else {
                throw new BadRequestException("Por favor, informar o ps do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.frequenciaRespiratoria != null) {
                historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
            } else {
                throw new BadRequestException("Por favor, informar o frequencia Respiratória do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.frequenciaCardiaca != null) {
                historicoClinico.frequenciaCardiaca = pHistoricoClinico.frequenciaCardiaca;
            } else {
                throw new BadRequestException("Por favor, informar o frequencia Cardíaca do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.pd != null) {
                historicoClinico.pd = pHistoricoClinico.pd;

            } else {
                throw new BadRequestException("Por favor, informar o pd do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.observacao != null) {
                historicoClinico.observacao = pHistoricoClinico.observacao;
            } else {
                throw new BadRequestException("Por favor, informar a observacao do Animal no Histórico Clínico.");//TODO organizar mensagem
            }
            if (pHistoricoClinico.pm != null) {
                historicoClinico.pm = pHistoricoClinico.pm;
            } else {
                throw new BadRequestException("Por favor, informar o pm do Animal no Histórico Clínico.");//TODO organizar mensagem
            }

            historicoClinico.isAtivo = Boolean.TRUE;
            historicoClinico.usuario = Usuario.find("email = ?1", email).firstResult();
            historicoClinico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
            historicoClinico.dataAcao = new Date();

            historicoClinico.persist();

        } else {
            throw new BadRequestException("Histórico Clínico já cadastrado!");
        }

    }

    public void updateHistoricoClinico(@NotNull HistoricoClinico pHistoricoClinico, String email) {

        historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico.id).firstResult();

        if (historicoClinico != null) {

            if (pHistoricoClinico.etco2 == null && pHistoricoClinico.temperaturaAnimal == null && pHistoricoClinico.spo2 == null && pHistoricoClinico.frequenciaRespiratoria == null && pHistoricoClinico.frequenciaCardiaca == null && pHistoricoClinico.ps == null && pHistoricoClinico.pd == null && pHistoricoClinico.pm == null) {
                throw new BadRequestException("Informe os dados para atualizar o Histórico Clínico.");//TODO organizar mensagem
            } else {
                if (pHistoricoClinico.etco2 != null) {
                    if (!historicoClinico.etco2.equals(pHistoricoClinico.etco2)) {
                        historicoClinico.etco2 = pHistoricoClinico.etco2;
                    }
                }
                if (pHistoricoClinico.temperaturaAnimal != null) {
                    if (!historicoClinico.temperaturaAnimal.equals(pHistoricoClinico.temperaturaAnimal)) {
                        historicoClinico.temperaturaAnimal = pHistoricoClinico.temperaturaAnimal;
                    }
                }
                if (pHistoricoClinico.spo2 != null) {
                    if (!historicoClinico.spo2.equals(pHistoricoClinico.spo2)) {
                        historicoClinico.spo2 = pHistoricoClinico.spo2;
                    }
                }
                if (pHistoricoClinico.frequenciaRespiratoria != null) {
                    if (!historicoClinico.frequenciaRespiratoria.equals(pHistoricoClinico.frequenciaRespiratoria)) {
                        historicoClinico.frequenciaRespiratoria = pHistoricoClinico.frequenciaRespiratoria;
                    }
                }
                if (pHistoricoClinico.frequenciaCardiaca != null) {
                    if (!historicoClinico.frequenciaCardiaca.equals(pHistoricoClinico.frequenciaCardiaca)) {
                        historicoClinico.frequenciaCardiaca = pHistoricoClinico.frequenciaCardiaca;
                    }
                }
                if (pHistoricoClinico.ps != null) {
                    if (!historicoClinico.ps.equals(pHistoricoClinico.ps)) {
                        historicoClinico.ps = pHistoricoClinico.ps;
                    }
                }
                if (pHistoricoClinico.observacao != null) {
                    if (!historicoClinico.observacao.equals(pHistoricoClinico.observacao)) {
                        historicoClinico.observacao = pHistoricoClinico.observacao;
                    }
                }
                if (pHistoricoClinico.pd != null) {
                    if (!historicoClinico.pd.equals(pHistoricoClinico.pd)) {
                        historicoClinico.pd = pHistoricoClinico.pd;
                    }
                }
                if (pHistoricoClinico.pm != null) {
                    if (!historicoClinico.pm.equals(pHistoricoClinico.pm)) {
                        historicoClinico.pm = pHistoricoClinico.pm;
                    }
                }
                if (pHistoricoClinico.animal != null) {
                    if (historicoClinico.animal != null && !historicoClinico.animal.equals(pHistoricoClinico.animal)) {
                        historicoClinico.animal = Animal.findById(pHistoricoClinico.animal.id);
                    }
                }
                historicoClinico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoClinico.dataAcao = new Date();
                historicoClinico.persist();
            }
        } else {
            throw new BadRequestException("Não foi possível atualizar o Histórico Clínico.");//TODO organizar mensagem

        }
    }

    public void deleteHistoricoClinico(List<Long> pListIdHistoricoClinico, String email) {

        pListIdHistoricoClinico.forEach((pHistoricoClinico) -> {
            historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = true ORDER BY id DESC", pHistoricoClinico).firstResult();

            if (historicoClinico != null) {
                historicoClinico.isAtivo = Boolean.FALSE;
                historicoClinico.dataAcao = new Date();
                historicoClinico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoClinico.systemDateDeleted = new Date();
                historicoClinico.persist();
            } else {
                if (pListIdHistoricoClinico.size() <= 1) {
                    throw new NotFoundException("Histórico Clínico não localizado ou já excluído.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Históricos Clínico não localizados ou já excluídos.");//TODO organizar mensagem
                }
            }
        });
    }

    public void reactivateHistoricoClinico(@NotNull List<Long> pListIdHistoricoClinico, String email) {

        pListIdHistoricoClinico.forEach((pHistoricoClinico) -> {
            historicoClinico = HistoricoClinico.find("id = ?1 and isAtivo = false ORDER BY id DESC", pHistoricoClinico).firstResult();

            if (historicoClinico != null) {
                historicoClinico.isAtivo = Boolean.TRUE;
                historicoClinico.dataAcao = new Date();
                historicoClinico.usuarioAcao = Usuario.find("email = ?1", email).firstResult();
                historicoClinico.systemDateDeleted = null;
                historicoClinico.persist();
            } else {
                if (pListIdHistoricoClinico.size() <= 1) {
                    throw new NotFoundException("Histórico Clínico não localizado ou já reativado.");//TODO organizar mensagem
                } else {
                    throw new NotFoundException("Históricos Clínico não localizados ou já reativados.");//TODO organizar mensagem
                }
            }
        });
    }
}
