package desafio.zoo.resources;

import desafio.zoo.controller.NutricaoController;
import desafio.zoo.model.Nutricao;
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
import java.util.stream.Collectors;

@Path("/nutricao")
public class NutricaoResources {

    @Inject
    NutricaoController controller;
    Nutricao nutricao;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        nutricao = Nutricao.findById(pId);
        return Response.ok(nutricao).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long nutricao = Nutricao.count(query);
        return Response.ok(nutricao).status(Response.Status.ACCEPTED).build();
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
            @QueryParam("animalAtivo") @DefaultValue("true") Boolean animalAtivo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<Nutricao> nutricao;
        nutricao = Nutricao.find(query);
        return Response.ok(nutricao.page(Page.of(pageIndex, pageSize)).list().stream().filter(c -> c.animal.isAtivo = animalAtivo).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(Nutricao pNutricao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addNutricao(pNutricao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível cadastrar a Ficha de Nutrição.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Nutricao pNutricao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateNutricao(pNutricao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar a Ficha de Nutrição.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListIdnutricao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteNutricao(pListIdnutricao, email);
        } catch (Exception e) {
            if (pListIdnutricao.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir a ficha de nutrição.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir as a fichas de nutrição.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListIdnutricao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateNutricao(pListIdnutricao, email);
        } catch (Exception e) {
            if (pListIdnutricao.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar a ficha de nutrição.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar as a fichas de nutrição.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

}