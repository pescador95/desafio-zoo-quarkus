package desafio.zoo.resources;

import desafio.zoo.controller.EnriquecimentoAmbientalController;
import desafio.zoo.model.EnriquecimentoAmbiental;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/enriquecimentoAmbiental")
public class EnriquecimentoAmbientalResources {

    @Inject
    EnriquecimentoAmbientalController controller;
    EnriquecimentoAmbiental EnriquecimentoAmbiental;
    List<EnriquecimentoAmbiental> enriquecimentoAmbientalList;
    Page page;


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        EnriquecimentoAmbiental = controller.getEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok(EnriquecimentoAmbiental).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        enriquecimentoAmbientalList = controller.getEnriquecimentoAmbientalListAtivos();
        return Response.ok(enriquecimentoAmbientalList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
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
    public Response add(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        controller.addEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        controller.updateEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        controller.deleteEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok().status(200).build();
    }
}
