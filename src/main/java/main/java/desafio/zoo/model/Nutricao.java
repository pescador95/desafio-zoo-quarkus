package main.java.desafiozoo.model;

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
    public Date dataInicio;
    @Column(nullable = false)
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

