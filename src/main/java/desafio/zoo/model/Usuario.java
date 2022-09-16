package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario")
@UserDefinition
public class Usuario extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(
            name = "usuarioIdSequence",
            sequenceName = "usuario_id_seq",
            allocationSize = 1,
            initialValue = 1
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

    @Column()
    public String usuarioAcao;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date systemDateDeleted;

   @Column()
   @Roles
    public String roleUsuario;

}
