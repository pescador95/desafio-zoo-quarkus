package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "monitoracao")
public class Monitoracao extends PanacheEntity {
    @Column(nullable = false)
    public Date dataMonitoracao;
    @Column()
    public Float temperaturaAnimal;
    @Column()
    public Float etco2;
    @Column()
    public Float spo2;
    @Column()
    public Float pd;
    @Column()
    public Float pm;
    @Column()
    public String frequenciaRespiratoria;
    @Column()
    public String frequenciaCariaca;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    @Column()
    public boolean isAtivo;
}

