package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medicacao")
@JsonIgnoreProperties({"usuario", "usuarioAcao", "isAtivo", "dataAcao", "systemDateDeleted"})
public class Medicacao extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "medicacaoIdSequence", sequenceName = "medicacao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "medicacaoIdSequence")
    @Id
    public Long id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public Medicacao() {

    }
}
