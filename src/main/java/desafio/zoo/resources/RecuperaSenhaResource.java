package desafio.zoo.resources;

import desafio.zoo.controller.RecuperaSenhaController;
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

@Path("/recuperaSenha")
public class RecuperaSenhaResource {
    @Inject
    RecuperaSenhaController controller;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response sendMail(@PathParam("email") String email) {
        System.out.println("Sendmail function OK");
        controller.sendEmail(email);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Usuario pUsuario, @Context @NotNull SecurityContext context, @QueryParam("password") String password) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updatePassword(email, password);
        return Response.ok().status(200).build();
    }
}