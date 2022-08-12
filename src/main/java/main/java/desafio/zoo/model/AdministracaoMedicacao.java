package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "administracaoMedicacao")
public class AdministracaoMedicacao extends PanacheEntity {
    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataMedicacao;
    @OneToOne
    @JoinColumn(name="userId")
    private Usuario usuario;
    @Column()
    public boolean isAtivo;
    @Column()
    public String usuarioAcao;
    @Column()
    //@JsonFormat(pattern="dd/MM/yyyy")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;
}

