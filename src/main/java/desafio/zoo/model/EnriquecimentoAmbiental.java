package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "enriquecimentoAmbiental")
public class EnriquecimentoAmbiental extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "enriquecimentoAmbientalIdSequence",
            sequenceName = "enriquecimentoAmbiental_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enriquecimentoAmbientalIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("enriquecimentoAmbiental")
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;

    @Column()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nomeAnimal;
    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataEnriquecimento;

    @Column()
    public String nomeEnriquecimento;

    @Column()
    public String descricaoEnriquecimento;

    @ManyToOne()
    @JsonIgnoreProperties("enriquecimentoAmbiental")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @ManyToOne()
    @JsonIgnoreProperties("enriquecimentoAmbiental")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @Column()
    public boolean isAtivo;


    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public EnriquecimentoAmbiental() {

    }
}

