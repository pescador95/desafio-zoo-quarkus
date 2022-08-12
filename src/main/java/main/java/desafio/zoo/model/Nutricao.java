package main.java.desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "nutricao")
public class Nutricao extends PanacheEntity {
    @OneToOne
    @JoinColumn(name="animalId")
    public Animal animal;
    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date dataInicio;
    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date dataFim;
    @Column(nullable = false)
    public String alimento;
    @Column()
    public String descricaoNutricao;
    @Column(nullable = false)
    public Float quantidade;
    @OneToOne
    @JoinColumn(name="userId")
    public Usuario usuario;
    @Column()
    public boolean isAtivo;
    @Column
    public int valorUnidadeMedida;
    @Column()
    public String usuarioAcao;
    @Column()
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    public java.util.Date dataAcao;

    public enum UnidadesMedida {
        GR(0),KG(1),TON(2),ML(3), L(4);

        @Column
        public int valorUnidadeMedida;
        UnidadesMedida(int valorUniMedida) {
            valorUnidadeMedida = valorUniMedida;
            //TODO Verificar sobre alimentação sazonal (períodos de acordo com estação do ano)
        }
    }
}

