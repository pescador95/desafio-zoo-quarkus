package desafio.zoo.resources;

import desafio.zoo.controller.AnimalController;
import desafio.zoo.model.Animal;
import desafio.zoo.model.Responses;
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
    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        animal = Animal.findById(pId);
        return Response.ok(animal).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long animais = Animal.count(query);
        return Response.ok(animais).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        PanacheQuery<Animal> animais;
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        animais = Animal.find(query);
        return Response.ok(animais.page(Page.of(pageIndex, pageSize)).list()).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(Animal pAnimal, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addAnimal(pAnimal, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 400;
            responses.messages.add("Não foi possível cadastrar o animal.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Animal pAnimal, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateAnimal(pAnimal, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar o animal.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListIdAnimal, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteAnimal(pListIdAnimal, email);
        } catch (Exception e) {
            if (pListIdAnimal.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir o animal.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir os animais.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListIdAnimal, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateAnimal(pListIdAnimal, email);
        } catch (Exception e) {
            if (pListIdAnimal.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar o animal.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar os animais.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}