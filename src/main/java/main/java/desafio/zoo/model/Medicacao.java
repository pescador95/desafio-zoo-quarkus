package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medicacao")
public class Medicacao extends PanacheEntity {
    @OneToOne
    @JoinColumn(name="administracaoMedicacaoId")
    private AdministracaoMedicacao administracaoMedicacao;
    @Column(nullable = false)
    public String nomeMedicacao;
    @Column()
    public String viaAdministracao;
    @Column()
    public String posologia;
    @Column(nullable = false)
    public String frequencia;
    @OneToOne
    @JoinColumn(name="userId")
    private Usuario usuario;
    @Column()
    public boolean isAtivo;
    @Column()
    public String usuarioAcao;
    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAcao;
}

