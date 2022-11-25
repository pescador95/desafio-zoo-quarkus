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

    @POST
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response sendMail(@PathParam("email") String email) {
        try {
            return controller.sendEmail(email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível localizar um cadastro com o email informado.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Usuario pUsuario, @Context @NotNull SecurityContext context,
            @QueryParam("password") String password) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updatePassword(email, password);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar a senha.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}