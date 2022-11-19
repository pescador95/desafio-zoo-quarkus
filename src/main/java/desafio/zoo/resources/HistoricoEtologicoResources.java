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
import java.util.Arrays;
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
        return Response.ok(historicoEtologico).status(200).build();
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
        return Response.ok(historicoEtologico).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<HistoricoEtologico> historicoEtologico;
        historicoEtologico = HistoricoEtologico.find(query);
        return Response.ok(historicoEtologico.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(HistoricoEtologico pHistoricoEtologico, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.status = 201;
        responses.messages.add("Histórico Etológico cadastrado com sucesso!");
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addHistoricoEtologico(pHistoricoEtologico, email);
        return Response.ok(responses).status(201, "Histórico Etológico cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(HistoricoEtologico pHistoricoEtologico, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.status = 200;
        responses.messages.add("Histórico Etológico atualizado com sucesso!");
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateHistoricoEtologico(pHistoricoEtologico, email);
        return Response.ok(responses).status(200, "Histórico Etológico atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListHistoricoEtologico, @Context @NotNull SecurityContext context) {
        Integer countList = pListHistoricoEtologico.size();
        responses = new Responses();
        responses.status = 200;
        if (pListHistoricoEtologico.size() <= 1) {
            responses.messages.add("Histórico Etológico excluído com sucesso!");
        } else {
            responses.messages.add(countList + " Históricos Etológicos exclúidos com sucesso!");
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteHistoricoEtologico(pListHistoricoEtologico, email);
        return Response.ok(responses).status(200, "Histórico Etológico excluído com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListHistoricoEtologico, @Context @NotNull SecurityContext context) {
        Integer countList = pListHistoricoEtologico.size();
        responses = new Responses();
        responses.status = 200;
        if (pListHistoricoEtologico.size() <= 1) {
            responses.messages.add("Histórico Etológico recuperado com sucesso!");
        } else {
            responses.messages.add(countList + " Históricos Etológicos recuperados com sucesso!");
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateHistoricoEtologico(pListHistoricoEtologico, email);
        return Response.ok(responses).status(200, "Histórico Etológico recuperado com sucesso!").build();
    }
}