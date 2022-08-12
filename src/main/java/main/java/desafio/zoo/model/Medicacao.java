package main.java.desafiozoo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

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
}
