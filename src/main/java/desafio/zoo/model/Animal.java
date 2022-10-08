package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "animal")
public class Animal extends PanacheEntityBase {


    @Column()
    @SequenceGenerator(
            name = "animalIdSequence",
            sequenceName = "animal_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animalIdSequence")
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
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("animal")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @ManyToOne()
    @JsonIgnoreProperties("animal")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;
    @Column()
    public String origem;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Nutricao.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("animal")
    List<Nutricao> nutricaoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = HistoricoEtologico.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("animal")
    List<HistoricoEtologico> historicoEtologicoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = HistoricoClinico.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("animal")
    List<HistoricoClinico> historicoClinicoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = EnriquecimentoAmbiental.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("animal")
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;

    public Animal() {

    }
}