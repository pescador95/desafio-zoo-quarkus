package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nutricao")
public class Nutricao extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date dataInicio;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date dataFim;

    @Column()
    public String descricaoNutricao;

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

