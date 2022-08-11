package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

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
    public ZonedDateTime dataAcao;
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
