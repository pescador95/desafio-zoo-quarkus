package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historicoClinico")
public class HistoricoClinico extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column()
    public String pelagem;

    @Column()
    public String diagnosticoInicial;

    @Column()
    public Float temperaturaAnimal;

    @Column()
    public String pulso;

    @Column()
    public String frequenciaRespiratoria;

    @Column()
    public String frequenciaCariaca;

    @Column()
    public Boolean terapiaPosCiclo;

    @Column()
    public String observacao;

    @Column()
    public Boolean isInternamento;

    @ManyToOne
    @JoinColumn(name = "internamentoId")
    public Internamento internamento;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAlta;

    @ManyToOne
    @JoinColumn(name = "laudoNecroscopicoId")
    public LaudoNecroscopico laudoNecroscopico;

    @ManyToOne
    @JoinColumn(name="userId")
    public Usuario usuario;

    @Column()
    public boolean isAtivo;

    @Column()
    public String usuarioAcao;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;
}

