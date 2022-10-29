package desafio.zoo.resources;

import desafio.zoo.controller.RecuperaSenhaController;
import desafio.zoo.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;

@Path("/recuperaSenha")
public class RecuperaSenhaResource {
    @Inject
    RecuperaSenhaController controller;
    String retorno;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    //@RolesAllowed({"veterinario", "biologo", "dev"})
    public Response sendMail(@PathParam("email") String email) {
        System.out.println("Sendmail function OK");
        retorno = controller.enviaEmail(email);
        return Response.ok(retorno).status(200).build();
    }
}
