package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "usuario")
@UserDefinition
public class Usuario extends PanacheEntity {

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
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
