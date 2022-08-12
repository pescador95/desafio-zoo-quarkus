package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "enriquecimentoAmbiental")
public class EnriquecimentoAmbiental extends PanacheEntity {
    @OneToOne
    @JoinColumn(name="animalId")
    private Animal animal;
    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataEnriquecimento;
    @Column(nullable = false)
    public String nomeEnriquecimento;
    @Column()
    public String descricaoEnriquecimento;
    @OneToOne
    @JoinColumn(name="userId")
    private Usuario usuario;
    @Column()
    public boolean isAtivo;
    @Column()
    public String usuarioAcao;
    @Column()
    //@JsonFormat(pattern="dd/MM/yyyy")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;
}

