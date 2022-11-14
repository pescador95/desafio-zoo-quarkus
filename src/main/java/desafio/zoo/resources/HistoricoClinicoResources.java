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
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        historicoClinico = HistoricoEtologico.findById(pId);
        return Response.ok(historicoClinico).status(200).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
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
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                         @QueryParam("page") @DefaultValue("0") int pageIndex,
                         @QueryParam("size") @DefaultValue("20") int pageSize,
                         @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                         @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
                         @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<HistoricoClinico> historicoClinico;
        historicoClinico = HistoricoClinico.find(query);
        return Response.ok(historicoClinico.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response add(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Histórico Clínico cadastrado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addHistoricoClinico(pHistoricoClinico, email);
        return Response.ok(responses).status(201, "Histórico Clínico cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Histórico Clínico atualizado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateHistoricoClinico(pHistoricoClinico, email);
        return Response.ok(responses).status(200, "Histórico Clínico atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdHistoricoClinico.size();
        responses = new Responses();
        if(pListIdHistoricoClinico.size() <= 1){
            responses.message = "Histórico Clínico excluído com sucesso!";
        } else {
            responses.message = countList + " Históricos Clínicos exclúidos com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteHistoricoClinico(pListIdHistoricoClinico, email);
        return Response.ok(responses).status(200, "Histórico Clínico excluído com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdHistoricoClinico.size();
        responses = new Responses();
        if(pListIdHistoricoClinico.size() <= 1){
            responses.message = "Histórico Clínico recuperado com sucesso!";
        } else {
            responses.message = countList + " Históricos Clínicos recuperados com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateHistoricoClinico(pListIdHistoricoClinico, email);
        return Response.ok(responses).status(200, "Histórico Clínico recuperado com sucesso!").build();
    }
}