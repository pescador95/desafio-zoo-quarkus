package desafio.zoo.resources;

import desafio.zoo.model.EnriquecimentoAmbiental;
import desafio.zoo.controller.EnriquecimentoAmbientalController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/enriquecimentoAmbiental")
public class EnriquecimentoAmbientalResources {

    @Inject
    EnriquecimentoAmbientalController controller;
    EnriquecimentoAmbiental EnriquecimentoAmbiental;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(EnriquecimentoAmbiental pEnriquecimentoAmbiental) {
        EnriquecimentoAmbiental = controller.getEnriquecimentoAmbiental(pEnriquecimentoAmbiental);
        return Response.ok(EnriquecimentoAmbiental).status(200).build();
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
