package desafio.zoo.services;

import desafio.zoo.model.Usuario;
import desafio.zoo.utils.AuthToken;

import javax.inject.Inject;

public class LoginServices {


    @Inject
    AuthToken authToken;
    Usuario usuario;

    public void login(){

        authToken.GenerateAccessToken(usuario); {



        }
    }
}
