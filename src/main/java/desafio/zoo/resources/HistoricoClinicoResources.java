package desafio.zoo.resources;

import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.controller.HistoricoClinicoController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/historicoClinico")
public class HistoricoClinicoResources {

    @Inject
    HistoricoClinicoController controller;
    HistoricoClinico HistoricoClinico;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(HistoricoClinico pHistoricoClinico) {
        HistoricoClinico = controller.getHistoricoClinico(pHistoricoClinico);
        return Response.ok(HistoricoClinico).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(HistoricoClinico pHistoricoClinico) {
        controller.addHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(HistoricoClinico pHistoricoClinico) {
        controller.updateHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(HistoricoClinico pHistoricoClinico) {
        controller.deleteHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(200).build();
    }
}
