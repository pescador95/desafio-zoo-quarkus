package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "animal")
@JsonIgnoreProperties({"usuario", "usuarioAcao", "isAtivo", "dataAcao", "systemDateDeleted"})
public class Animal extends PanacheEntityBase {


    @Column()
    @SequenceGenerator(
            name = "animalIdSequence",
            sequenceName = "animal_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "animalIdSequence")
    @Id
    public Long id;

    @Column()
    public String nomeComum;

    @Column()
    public String nomeCientifico;

    @Column()
    public String nomeApelido;

    @Column()
    public String identificacao;

    @Column()
    public String sexo;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy")
    public Date dataEntrada;

    @Column()
    public String idade;

    @Column()
    public String origem;

    @Column()
    public String orgao;

    @Column()
    @JsonIgnore
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnore
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

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Nutricao> nutricaoList;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<HistoricoEtologico> historicoEtologicoList;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<HistoricoClinico> historicoClinicoList;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Profile> profileList;

    public Animal() {

    }
}