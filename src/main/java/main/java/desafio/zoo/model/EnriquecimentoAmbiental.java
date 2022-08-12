package main.java.desafiozoo.model;

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
}

