package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historicoEtologico")
public class HistoricoEtologico extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "historicoEtologicoIdSequence",
            sequenceName = "historicoEtologico_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "historicoEtologicoIdSequence")
    @Id
    public Long id;

    @ManyToOne()
    @JsonIgnoreProperties("historicoEtologico")
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;

    @Column()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String nomeAnimal;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataEtologico;

    @Column(nullable = false)
    public String nomeEtologico;

    @Column()
    public String descricaoEtologico;

    @Column()
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("historicoEtologico")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @ManyToOne()
    @JsonIgnoreProperties("historicoEtologico")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public HistoricoEtologico() {

    }
}

