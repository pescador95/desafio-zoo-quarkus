package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"usuario", "usuarioAcao", "isAtivo", "dataAcao", "systemDateDeleted"})
@Table(name = "historicoEtologico")
public class HistoricoEtologico extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "historicoEtologicoIdSequence", sequenceName = "historicoEtologico_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "historicoEtologicoIdSequence")
    @Id
    public Long id;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;

    @Column()
    public String nomeAnimal;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date dataEtologico;

    @Column(nullable = false)
    public String nomeEtologico;

    @Column()
    public String descricaoEtologico;

    @Column()
    @JsonIgnore
    public boolean isAtivo;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    public String usuarioNome;

    @Column()
    public String usuarioAcaoNome;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public HistoricoEtologico() {

    }
}
