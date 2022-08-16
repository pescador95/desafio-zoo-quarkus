package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "usuario")
//@UserDefinition
public class Usuario extends PanacheEntity {

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
   // @Username
    public String login;

    @Column(nullable = false)
   // @Password
    public String password;

    @Column(nullable = false)
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
    public int roleUsuario;

   public static final int NORMAL = 0;
   public static final int ADMIN = 1;



}
