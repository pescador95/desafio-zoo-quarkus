package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicoClinicoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column()
    public Float temperaturaAnimal;

    @Column()
    public String frequenciaRespiratoria;

    @Column()
    public String frequenciaCariaca;

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

