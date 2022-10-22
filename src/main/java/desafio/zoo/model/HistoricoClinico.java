package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historicoClinico")
public class HistoricoClinico extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "historicoClinicoIdSequence",
            sequenceName = "historicoClinico_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "historicoClinicoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("historicoClinico")
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;

    @Column()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nomeAnimal;

    @Column()
    public Float temperaturaAnimal;

    @Column()
    public String frequenciaRespiratoria;

    @Column()
    public String frequenciaCardiaca;

    @Column()
    public String observacao;

    @Column()
    public Float etco2;

    @Column()
    public Float spo2;

    @Column()
    public Float ps;

    @Column()
    public Float pd;

    @Column()
    public Float pm;

    @Column()
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("historicoClinico")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @ManyToOne()
    @JsonIgnoreProperties("historicoClinico")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public HistoricoClinico() {

    }
}

