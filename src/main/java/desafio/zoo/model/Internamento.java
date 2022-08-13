package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "internamento")
public class Internamento extends PanacheEntity {

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date inicioInternamento;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date fimInternamento;

    @OneToOne
    @JoinColumn(name = "monitoracaoId")
    public Monitoracao monitoracao;

    @Column()
    public Boolean isObito;

    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public Date dataAlta;

    @OneToOne
    @JoinColumn(name = "laudoNecroscopicoId", unique = true)
    public LaudoNecroscopico laudoNecroscopico;

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

