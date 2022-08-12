package main.java.desafio.zoo.resources;

import main.java.desafio.zoo.model.Nutricao;
import main.java.desafio.zoo.controller.NutricaoController;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/nutricao")
public class NutricaoResources {

    @Inject
    NutricaoController controller;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Nutricao pNutricao) {
        Nutricao nutricao = controller.getNutricao(pNutricao);
        return Response.ok(nutricao).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Nutricao pNutricao) {
        controller.addNutricao(pNutricao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Nutricao pNutricao) {
        controller.updateNutricao(pNutricao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Nutricao pNutricao) {
        controller.deleteNutricao(pNutricao);
        return Response.ok().status(200).build();
    }
}
