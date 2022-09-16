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
import java.util.Set;

@Entity
@Table(name = "usuario")
@UserDefinition
public class Usuario extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "usuarioIdSequence",
            sequenceName = "usuario_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioIdSequence")
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
    public boolean isAtivo;

    @ManyToOne()
    @JsonIgnoreProperties("usuario")
    @JoinColumn(name = "userId")
    @GeneratedValue
    public Usuario usuarioAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

    @Column()
    @Roles
    public String roleUsuario;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Nutricao.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "animal")
    @JsonIgnoreProperties("usuario")
    List<Nutricao> nutricaoList;

    public Usuario() {

    }
}
