package desafio.zoo.resources;

import desafio.zoo.controller.AnimalController;
import desafio.zoo.model.Animal;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;


@Path("/animal")
public class AnimalResources {

    @Inject
    AnimalController controller;
    Animal animal;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        animal = Animal.findById(pId);
        return Response.ok(animal).status(200).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo) {
        long animais = Animal.count("isAtivo = ?1", ativo);
        return Response.ok(animais).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                         @QueryParam("page") @DefaultValue("0") int pageIndex,
                         @QueryParam("size") @DefaultValue("20") int pageSize,
                         @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                         @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
                         @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {
        PanacheQuery<Animal> animais;

        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;

        animais = Animal.find(query);

        return Response.ok(animais.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response add(Animal pAnimal, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addAnimal(pAnimal, email);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Animal pAnimal, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateAnimal(pAnimal, email);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListIdAnimal, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteAnimal(pListIdAnimal, email);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListIdAnimal, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateAnimal(pListIdAnimal, email);
        return Response.ok().status(200).build();
    }
}

