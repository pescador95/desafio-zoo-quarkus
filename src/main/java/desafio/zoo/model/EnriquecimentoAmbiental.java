package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "enriquecimentoAmbiental")
@JsonIgnoreProperties({"usuario", "usuarioAcao", "isAtivo", "dataAcao", "systemDateDeleted"})
public class EnriquecimentoAmbiental extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "enriquecimentoAmbientalIdSequence", sequenceName = "enriquecimentoAmbiental_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "enriquecimentoAmbientalIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;

    @Column()
    public String nomeAnimal;
    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date dataEnriquecimento;

    @Column()
    public String nomeEnriquecimento;

    @Column()
    public String descricaoEnriquecimento;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @Column()
    public String usuarioNome;

    @Column()
    public String usuarioAcaoNome;

    @Column()
    @JsonIgnore
    public boolean isAtivo;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public EnriquecimentoAmbiental() {

    }
}
