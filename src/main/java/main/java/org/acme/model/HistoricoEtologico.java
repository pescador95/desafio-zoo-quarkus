package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historicoEtologico")
public class HistoricoEtologico extends PanacheEntity {
    @OneToOne
    @JoinColumn(name="animalId")
    private Animal animal;
    @Column(nullable = false)
    public Date dataEtologico;
    @Column(nullable = false)
    public String nomeEtologico;
    @Column()
    public String descricaoEtologico;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    @Column()
    public boolean isAtivo;
}

