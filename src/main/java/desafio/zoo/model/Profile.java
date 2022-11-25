package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile extends PanacheEntityBase {

    @Column()
    @SequenceGenerator(name = "profileIdSequence", sequenceName = "profile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "profileIdSequence")
    @Id
    public Long id;

    @Column()
    public String originalName;

    @Column()
    public String keyName;

    @Column()
    public String mimetype;

    @Column()
    public Date dataCriado;

    @Column()
    public Long fileSize;

    @ManyToOne()
    @JsonIgnoreProperties("profile")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "animalId")
    @GeneratedValue
    public Animal animal;
    @Column()
    public String fileReference;

    @Column()
    public String nomeAnimal;

    @Column()
    public Boolean isAtivo;

    public Profile() {

    }
}