package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "[user]")
public class User extends PanacheEntity {
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
    public int roleUser;

    public enum RolesUser {
        NORMAL(0), ADMIN(1), VETERINARIO(2), ZOOLOGO(3), ANESTESISTA(4);

        @Column
        public int valorRoleUser;

        RolesUser(int roleUser) {
            valorRoleUser = roleUser;
        }
    }
}
