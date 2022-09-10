package desafio.zoo.model;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@ApplicationScoped
public class Auth {
    public String login;
    public String password;
    public Usuario usuario;
    public String accessToken;
    public Date expireDateAccessToken;
    public String refreshToken;
    public Date expireDateRefreshToken;
}
