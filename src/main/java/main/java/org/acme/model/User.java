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

    public static final int NORMAL = 1;
    public static final int ADMIN = 2;
}
