package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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
            allocationSize = 1,
            initialValue = 1
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataEntrada;

    @Column()
    public String idade;

    @Column()
    public boolean isAtivo;

    @Column()
    public String usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;
   @Column()
    public String origem;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("animal")
   List<Nutricao> nutricao;
}