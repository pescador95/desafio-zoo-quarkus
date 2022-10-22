package desafio.zoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@ApplicationScoped
public class Auth {

    public String email;

    public String nomeUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    public String roleUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Usuario usuario;
    public String accessToken;
    public Date expireDateAccessToken;
    public String refreshToken;
    public Date expireDateRefreshToken;
}
