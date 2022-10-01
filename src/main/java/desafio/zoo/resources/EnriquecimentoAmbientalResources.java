package desafio.zoo.resources;

import desafio.zoo.controller.EnriquecimentoAmbientalController;
import desafio.zoo.model.EnriquecimentoAmbiental;
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
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;
    Page page;


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response get(@PathParam("pId") Long pId) {
        enriquecimentoAmbiental = enriquecimentoAmbiental.findById(pId);
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
        page = Page.of(pageIndex, pageSize);
        enriquecimentoAmbientalList = controller.getEnriquecimentoAmbientalListAtivos();
        return Response.ok(enriquecimentoAmbientalList).status(200).build();
    }

    @GET
    @Path("/inativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        enriquecimentoAmbientalList = controller.getEnriquecimentoAmbientalListInativos();
        return Response.ok(enriquecimentoAmbientalList).status(200).build();
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
    public Response deleteList(List<EnriquecimentoAmbiental> enriquecimentoAmbientalList, @QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.deleteEnriquecimentoAmbiental(enriquecimentoAmbientalList);
        return Response.ok().status(200).build();
    }
    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<EnriquecimentoAmbiental> enriquecimentoAmbientalList, @QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.reactivateEnriquecimentoAmbiental(enriquecimentoAmbientalList);
        return Response.ok().status(200).build();
    }
}
