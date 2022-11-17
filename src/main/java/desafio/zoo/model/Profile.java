package desafio.zoo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile {

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

    public Profile(){

    }
}
