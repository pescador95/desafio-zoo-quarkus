package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nutricaoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("animal")
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date dataInicio;

    @Column()
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

