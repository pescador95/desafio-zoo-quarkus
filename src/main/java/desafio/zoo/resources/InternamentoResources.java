package desafio.zoo.resources;

import desafio.zoo.model.Internamento;
import desafio.zoo.controller.InternamentoController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/Internamento")
public class InternamentoResources {

    @Inject
    InternamentoController controller;
    Internamento Internamento;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Internamento pInternamento) {
        Internamento = controller.getInternamento(pInternamento);
        return Response.ok(Internamento).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Internamento pInternamento) {
        controller.addInternamento(pInternamento);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Internamento pInternamento) {
        controller.updateInternamento(pInternamento);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Internamento pInternamento) {
        controller.deleteInternamento(pInternamento);
        return Response.ok().status(200).build();
    }
}
