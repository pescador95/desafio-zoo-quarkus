//package desafio.zoo.utils;
//
//import io.smallrye.jwt.build.Jwt;
//import desafio.zoo.model.Usuario;
//import org.eclipse.microprofile.config.inject.ConfigProperty;
//
//import javax.enterprise.context.ApplicationScoped;
//import java.util.Arrays;
//import java.util.HashSet;
//
//@ApplicationScoped
//public class GenerateToken {
//
//    @ConfigProperty(name = "mp.jwt.verify.issuer")
//    String issuer;
//
//    public String generateTokenJWT(Usuario pUsuario) {
//        String token =
//                Jwt.issuer(issuer)
//                        .upn(pUsuario.login)
//                        .groups(new HashSet<>(Arrays.asList("Usuario")))
//                        .sign();
//        System.out.println(token);
//        return token;
//    }
//}