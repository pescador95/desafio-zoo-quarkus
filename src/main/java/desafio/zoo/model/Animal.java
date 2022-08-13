package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataEntrada;

    @Column()
    public int idade;

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
// T   @Column()
//    public boolean origem TODO DESCOBRIR O QUE É O DADO ORIGEM;
}
