package main.java.desafiozoo.model;

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
    private Usuario usuario;
    @Column()
    public boolean isAtivo;
}
