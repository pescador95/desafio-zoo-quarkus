package desafio.zoo.resources;

import desafio.zoo.controller.AnimalController;
import desafio.zoo.model.Animal;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/animal")
public class AnimalResources {

    @Inject
    AnimalController controller;
    Animal animal;
    List<Animal> animalList;
    Page page;


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Animal pAnimal) {
        animal = controller.getAnimal(pAnimal);
        return Response.ok(animal).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        animalList = controller.getAnimalListAtivos();
        return Response.ok(animalList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        animalList = controller.getAnimalListInativos();
        return Response.ok(animalList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Animal pAnimal) {
        controller.addAnimal(pAnimal);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Animal pAnimal) {
        controller.updateAnimal(pAnimal);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Animal pAnimal) {
        controller.deleteAnimal(pAnimal);
        return Response.ok().status(200).build();
    }
}
