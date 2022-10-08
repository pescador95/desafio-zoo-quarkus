package desafio.zoo.resources;

import desafio.zoo.controller.EnriquecimentoAmbientalController;
import desafio.zoo.model.EnriquecimentoAmbiental;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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


@Path("/enriquecimentoAmbiental")
public class EnriquecimentoAmbientalResources {

    @Inject
    EnriquecimentoAmbientalController controller;
    EnriquecimentoAmbiental enriquecimentoAmbiental;
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        enriquecimentoAmbiental = PanacheEntityBase.findById(pId);
        return Response.ok(enriquecimentoAmbiental).status(200).build();
    }
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true")  Boolean ativo) {
        long enriquecimentoAmbiental = EnriquecimentoAmbiental.count("isAtivo = ?1", ativo);
        return Response.ok(enriquecimentoAmbiental).status(200).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") @NotNull String sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize,
                               @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {
        PanacheQuery<EnriquecimentoAmbiental> enriquecimentoAmbiental;
        if (sortQuery.equals("desc")) {
            enriquecimentoAmbiental = EnriquecimentoAmbiental.find("isAtivo = ?1 order by id desc", ativo);
            return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
        } else {
            enriquecimentoAmbiental = EnriquecimentoAmbiental.find("isAtivo = ?1 order by id asc", ativo);
            return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
        }
    }
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(EnriquecimentoAmbiental pEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(EnriquecimentoAmbiental pEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        return Response.ok().status(200).build();
    }
    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        return Response.ok().status(200).build();
    }
}
