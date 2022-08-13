package desafio.zoo.resources;

import desafio.zoo.model.LaudoNecroscopico;
import desafio.zoo.controller.LaudoNecroscopicoController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/laudoNecroscopico")
public class LaudoNecroscopicoResources {

    @Inject
    LaudoNecroscopicoController controller;
    LaudoNecroscopico laudonecroscopico;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(LaudoNecroscopico pLaudoNecroscopico) {
        laudonecroscopico = controller.getLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok(laudonecroscopico).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(LaudoNecroscopico pLaudoNecroscopico) {
        controller.addLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(LaudoNecroscopico pLaudoNecroscopico) {
        controller.updateLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(LaudoNecroscopico pLaudoNecroscopico) {
        controller.deleteLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(200).build();
    }
}
