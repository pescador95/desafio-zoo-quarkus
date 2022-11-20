package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoEtologicoController;
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

@Path("/historicoEtologico")
public class HistoricoEtologicoResources {

    @Inject
    HistoricoEtologicoController controller;
    HistoricoEtologico historicoEtologico;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        historicoEtologico = HistoricoEtologico.findById(pId);
        return Response.ok(historicoEtologico).status(Response.Status.ACCEPTED).build();
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
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<HistoricoEtologico> historicoEtologico;
        historicoEtologico = HistoricoEtologico.find(query);
        return Response.ok(historicoEtologico.page(Page.of(pageIndex, pageSize)).list())
                .status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(HistoricoEtologico pHistoricoEtologico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addHistoricoEtologico(pHistoricoEtologico, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível cadastrar o histórico etológico.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(HistoricoEtologico pHistoricoEtologico, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateHistoricoEtologico(pHistoricoEtologico, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar o Histórico Etológico.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }

    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListHistoricoEtologico, @Context @NotNull SecurityContext context) {

        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteHistoricoEtologico(pListHistoricoEtologico, email);
        } catch (Exception e) {
            if (pListHistoricoEtologico.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir o Histórico Etológico.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir os Históricos Etológicos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListHistoricoEtologico, @Context @NotNull SecurityContext context) {

        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateHistoricoEtologico(pListHistoricoEtologico, email);
        } catch (Exception e) {
            if (pListHistoricoEtologico.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar o Histórico Etológico.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível reativar os Históricos Etológicos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}