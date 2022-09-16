package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enriquecimentoAmbientalIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataEnriquecimento;

    @Column()
    public String nomeEnriquecimento;

    @Column()
    public String descricaoEnriquecimento;

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

