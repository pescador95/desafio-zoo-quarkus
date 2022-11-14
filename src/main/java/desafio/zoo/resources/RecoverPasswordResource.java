package desafio.zoo.resources;

import desafio.zoo.controller.RecoverPasswordController;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/recoverPassword")

public class RecoverPasswordResource {
    @Inject
    RecoverPasswordController controller;

    Responses responses;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response sendMail(@PathParam("email") String email) {
        responses = new Responses();
        responses.message = "Uma nova senha foi enviada para recuperar ao email informado.";
        controller.sendEmail(email);
        return Response.ok(responses).status(200, "Uma nova senha foi enviada para recuperar ao email informado.").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Usuario pUsuario, @Context @NotNull SecurityContext context, @QueryParam("password") String password) {
        responses = new Responses();
        responses.message = "Senha alterada com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updatePassword(email, password);
        return Response.ok(responses).status(200,"Senha alterada com sucesso!").build();
    }
}