package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntity {
    @Column(nullable = false)
    public String nome;
    @Column(nullable = false, unique = true)
    public String login;
    @Column(nullable = false)
    public String password;
    @Column(nullable = false, unique = true)
    public String email;
    @Column()
    public boolean isAtivo;
    @Column()
    public String usuarioAcao;
    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;
   @Column()
    public int roleUsuario;

    public enum RoleUsuarios {
        NORMAL(0), ADMIN(1), VETERINARIO(2), ZOOLOGO(3), ANESTESISTA(4);

        @Column
        public int valorRoleUsuario;

        RoleUsuarios(int roleUsuario) {
            valorRoleUsuario = roleUsuario;
        }
    }
}
