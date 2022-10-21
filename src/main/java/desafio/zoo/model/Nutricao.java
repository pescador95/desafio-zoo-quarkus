package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nutricao")
public class Nutricao extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "nutricaoIdSequence",
            sequenceName = "nutricao_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nutricaoIdSequence")
    @Id
    public Long id;


    @ManyToOne()
    @JsonIgnoreProperties("nutricao")
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public
    Animal animal;

    @Column()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nomeAnimal;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date dataInicio;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date dataFim;

    @Column()
    public String descricaoNutricao;

    @ManyToOne()
    @JsonIgnoreProperties("nutricao")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @Column()
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("nutricao")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public Nutricao() {

    }

}

