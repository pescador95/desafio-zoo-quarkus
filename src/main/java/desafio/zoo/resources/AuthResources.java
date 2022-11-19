package desafio.zoo.resources;

import desafio.zoo.controller.AuthController;
import desafio.zoo.model.Auth;
import desafio.zoo.model.Responses;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/auth")
public class AuthResources {

    @Inject
    AuthController authController;
    Auth auth;
    Responses responses;
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response auth(Auth data) {
        try{
            auth = authController.login(data);
            return Response.ok(auth).status(200).build();
        } catch (Exception e){
            return Response.ok(responses).status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response refreshToken(Auth data) {
        try{
            auth = authController.refreshToken(data);
            return Response.ok(auth).status(200).build();
        }catch (Exception e) {
            return Response.ok(responses).status(Response.Status.UNAUTHORIZED).build();}

    }
}