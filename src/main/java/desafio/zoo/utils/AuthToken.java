package desafio.zoo.utils;

import io.smallrye.jwt.build.Jwt;
import desafio.zoo.model.Usuario;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class AuthToken {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    public String GenerateAccessToken(Usuario pUsuario) {
        String accessToken = Jwt.issuer(this.issuer)
                .upn(pUsuario.email)
                .groups(new HashSet<>(Arrays.asList("Usuario", "Admin")))
                .expiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                .sign();
        return accessToken;
    }

    public String GenerateRefreshToken(Usuario pUsuario) {
        String refreshToken = Jwt.issuer(this.issuer)
                .upn(pUsuario.email)
                .groups(String.valueOf(pUsuario.roleUsuario))
                .expiresAt(Instant.now().plus(Duration.ofDays(7)))
                .sign();
        return refreshToken;
    }
}
