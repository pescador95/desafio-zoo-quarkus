package desafio.zoo.resources;

import desafio.zoo.model.HistoricoEtologico;
import desafio.zoo.controller.HistoricoEtologicoController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/historicoEtologico")
public class HistoricoEtologicoResources {

    @Inject
    HistoricoEtologicoController controller;
    HistoricoEtologico HistoricoEtologico;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(HistoricoEtologico pHistoricoEtologico) {
        HistoricoEtologico = controller.getHistoricoEtologico(pHistoricoEtologico);
        return Response.ok(HistoricoEtologico).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(HistoricoEtologico pHistoricoEtologico) {
        controller.addHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(HistoricoEtologico pHistoricoEtologico) {
        controller.updateHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(HistoricoEtologico pHistoricoEtologico) {
        controller.deleteHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(200).build();
    }
}
