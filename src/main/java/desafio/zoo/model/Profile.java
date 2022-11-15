package desafio.zoo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column()
    private String originalName;

    @Column()
    private String keyName;

    @Column()
    private String mimetype;

    @Column()
    private Date dataCriado;

    @Column()
    private Long fileSize;
    public Profile(){

    }
    public Long getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getMimetype() {
        return mimetype;
    }

    public Long getFilesize() {
        return fileSize;
    }

    public void setOriginalName(String fileName) {
        this.originalName = originalName;
    }

    public void setKeyName(String fileName) {
        this.keyName = keyName;
    }

    public void setMimetype(String contentType) {
        this.mimetype = mimetype;
    }

    public void setFileSize(long size) {
        this.fileSize = fileSize;
    }

    public void setDataCriada(Date date) {
        this.dataCriado = dataCriado;
    }
}
