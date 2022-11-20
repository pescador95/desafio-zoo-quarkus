package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@UserDefinition
public class Usuario extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "usuarioIdSequence", sequenceName = "usuario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "usuarioIdSequence")
    @Id
    public Long id;
    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Password
    public String password;

    @Column(nullable = false)
    @Username
    public String email;

    @Column()
    @Roles
    public String roleUsuario;

    @Column()
    public boolean isAtivo;

    @Column()
    public String usuario;

    @Column()
    public String usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Nutricao.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<Nutricao> nutricaoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Medicacao.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<Medicacao> medicacaoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = HistoricoEtologico.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<HistoricoEtologico> historicoEtologicoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = HistoricoClinico.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<HistoricoClinico> historicoClinicoList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = EnriquecimentoAmbiental.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Animal.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    List<Animal> animalList;

    public Usuario() {

    }

}
