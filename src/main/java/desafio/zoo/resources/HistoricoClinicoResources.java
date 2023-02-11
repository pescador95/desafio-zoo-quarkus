package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoClinicoController;
import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.HistoricoEtologico;
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

@Path("/historicoClinico")
public class HistoricoClinicoResources {

    @Inject
    HistoricoClinicoController controller;
    HistoricoClinico historicoClinico;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        historicoClinico = HistoricoClinico.findById(pId);
        return Response.ok(historicoClinico).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long historicoEtologico = HistoricoEtologico.count(query);
        return Response.ok(historicoEtologico).status(Response.Status.ACCEPTED).build();
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
        PanacheQuery<HistoricoClinico> historicoClinico;
        historicoClinico = HistoricoClinico.find(query);
        return Response.ok(historicoClinico.page(Page.of(pageIndex, pageSize)).list().stream().filter(c -> c.animal.isAtivo = animalAtivo).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }
    @GET
    @Path("/seletor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                         @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                         @QueryParam("animalAtivo") @DefaultValue("true") Boolean animalAtivo,
                         @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<HistoricoClinico> historicoClinico;
        historicoClinico = HistoricoClinico.find(query);
        return Response.ok(historicoClinico.list().stream().filter(c -> c.animal.isAtivo = animalAtivo).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addHistoricoClinico(pHistoricoClinico, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível cadastrar o Histórico Clínico.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateHistoricoClinico(pHistoricoClinico, email);
        } catch (Exception e) {
        }
        responses = new Responses();
        responses.status = 500;
        responses.messages.add("Não foi possível atualizar o Histórico Clínico.");
        return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteHistoricoClinico(pListIdHistoricoClinico, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListIdHistoricoClinico.size() <= 1) {
                responses.messages.add("Não foi possível excluir o Histórico Clínico.");
            } else {
                responses.messages.add("Não foi possível excluir os Históricos Clínicos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateHistoricoClinico(pListIdHistoricoClinico, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListIdHistoricoClinico.size() <= 1) {
                responses.messages.add("Não foi possível reativar o Histórico Clínico.");
            } else {
                responses.messages.add("Não foi possível reativar os Históricos Clínicos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
