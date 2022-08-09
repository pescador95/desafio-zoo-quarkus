package main.java.org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "administracaoMedicacao")
public class AdministracaoMedicacao extends PanacheEntity {
    @Column(nullable = false)
    public Date dataMedicacao;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    @Column()
    public boolean isAtivo;
}

