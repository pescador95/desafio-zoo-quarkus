package main.java.desafiozoo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "laudoNecroscopico")
public class LaudoNecroscopico extends PanacheEntity {
    @OneToOne
    @JoinColumn(name="animalId", unique = true)
    private Animal animal;
    @Column(unique = true)
    public long numeroLaudo;
    @Column(nullable = false)
    public Date dataObito;
    @Column(nullable = false)
    public Date dataNecropsia;
    @Column(nullable = false)
    public Boolean isEutanasia;
    @Column(nullable = false)
    public Boolean isespontanea;
    @Column(nullable = false)
    public String tipoMorte;
    @Column(nullable = false)
    public Boolean isLaudoEntregue;
    @OneToOne
    @JoinColumn(name="userId")
    private Usuario usuario;
    @Column()
    public boolean isAtivo;
}

