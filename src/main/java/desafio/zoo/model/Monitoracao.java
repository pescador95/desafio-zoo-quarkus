package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "monitoracao")
public class Monitoracao extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name="animalId")
    public Animal animal;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataMonitoracao;

    @Column()
    public Float temperaturaAnimal;

    @Column()
    public Float etco2;

    @Column()
    public Float spo2;

    @Column()
    public Float pd;

    @Column()
    public Float pm;

    @Column()
    public String frequenciaRespiratoria;

    @Column()
    public String frequenciaCariaca;

    @ManyToOne
    @JoinColumn(name="userId")
    public Usuario usuario;

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
}

