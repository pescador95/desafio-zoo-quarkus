package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medicacao")
public class Medicacao extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "medicacaoIdSequence", sequenceName = "medicacao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "medicacaoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("medicacao")
    @JoinColumn(name = "historicoClinicoId")
    @GeneratedValue
    public HistoricoClinico historicoClinico;

    @Column(nullable = false)
    public String nomeMedicacao;

    @Column()
    public String viaAdministracao;

    @Column()
    public String posologia;

    @Column(nullable = false)
    public String frequencia;

    @Column()
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("medicacao")
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @GeneratedValue
    public Usuario usuario;

    @ManyToOne()
    @JsonIgnoreProperties("medicacao")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    public Medicacao() {

    }
}
