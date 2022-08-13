package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "laudoNecroscopico")
public class LaudoNecroscopico extends PanacheEntity {

    @OneToOne
    @JoinColumn(name="animalId", unique = true)
    public Animal animal;

    @Column(unique = true)
    public long numeroLaudo;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataObito;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataNecropsia;

    @Column(nullable = false)
    public Boolean isEutanasia;

    @Column(nullable = false)
    public Boolean isEspontanea;

    @Column(nullable = false)
    public String tipoMorte;

    @Column(nullable = false)
    public Boolean isLaudoEntregue;

    @OneToOne
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

