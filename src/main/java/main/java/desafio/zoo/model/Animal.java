package main.java.desafiozoo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "animal")
public class Animal extends PanacheEntity {
    @Column(nullable = false)
    public String nomeComum;
    @Column(nullable = false)
    public String nomeCientifico;
    @Column(nullable = false)
    public String nomeApelido;
    @Column(nullable = false, unique = true)
    public String identificacao;
    @Column(nullable = false)
    public boolean sexo;
    @Column(nullable = false)
    public Date dataEntrada;
    @Column()
    public int idade;
    @Column()
    public boolean isAtivo;
// T   @Column()
//    public boolean origem TODO DESCOBRIR O QUE É O DADO ORIGEM;
}
