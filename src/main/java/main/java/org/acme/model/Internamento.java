package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "internamento")
public class Internamento extends PanacheEntity {
    @Column(nullable = false)
    public Date inicioInternamento;
    @Column()
    public Date fimInternamento;
    @OneToOne
    @JoinColumn(name = "monitoracaoId")
    private Monitoracao monitoracao;
    @Column()
    public Boolean isObito;
    @Column()
    public Date dataAlta;
    @OneToOne
    @JoinColumn(name = "laudoNecroscopicoId", unique = true)
    private LaudoNecroscopico laudoNecroscopico;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    @Column()
    public boolean isAtivo;
}

