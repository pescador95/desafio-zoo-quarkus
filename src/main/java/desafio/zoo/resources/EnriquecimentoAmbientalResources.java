package desafio.zoo.resources;

import desafio.zoo.controller.EnriquecimentoAmbientalController;
import desafio.zoo.model.EnriquecimentoAmbiental;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getEnriquecimentoAmbientalById(@PathParam("id") Long pId) {
        enriquecimentoAmbiental = PanacheEntityBase.findById(pId);
        return Response.ok(enriquecimentoAmbiental).status(200).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<EnriquecimentoAmbiental> enriquecimentoAmbiental =  EnriquecimentoAmbiental.find("isAtivo", true);
        return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @GET
    @Path("/inativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<EnriquecimentoAmbiental> enriquecimentoAmbiental =  EnriquecimentoAmbiental.find("isAtivo", false);
        return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        controller.addEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        controller.updateEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<EnriquecimentoAmbiental> enriquecimentoAmbientalList) {
        controller.deleteEnriquecimentoAmbiental(enriquecimentoAmbientalList);
        return Response.ok().status(200).build();
    }
    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<EnriquecimentoAmbiental> enriquecimentoAmbientalList) {
        controller.reactivateEnriquecimentoAmbiental(enriquecimentoAmbientalList);
        return Response.ok().status(200).build();
    }
}
