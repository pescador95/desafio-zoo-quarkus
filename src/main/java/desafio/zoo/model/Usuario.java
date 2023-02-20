package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@UserDefinition
@JsonIgnoreProperties({"usuario", "usuarioAcao", "isAtivo", "dataAcao", "systemDateDeleted"})
public class Usuario extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "usuarioIdSequence", sequenceName = "usuario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "usuarioIdSequence")
    @Id
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    @Password
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @Column(nullable = false)
    @Username
    public String email;

    @Column()
    @Roles
    public String roleUsuario;

    @Column()
    @JsonIgnore
    public boolean isAtivo;

    @Column()
    @JsonIgnore
    public String usuario;

    @Column()
    @JsonIgnore
    public String usuarioAcao;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Nutricao> nutricaoList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Medicacao> medicacaoList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<HistoricoEtologico> historicoEtologicoList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<HistoricoClinico> historicoClinicoList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Animal> animalList;

    public Usuario() {

    }

}
