package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicoEtologicoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataEtologico;

    @Column(nullable = false)
    public String nomeEtologico;

    @Column()
    public String descricaoEtologico;

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

