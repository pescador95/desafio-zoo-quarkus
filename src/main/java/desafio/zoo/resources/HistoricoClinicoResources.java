package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoClinicoController;
import desafio.zoo.model.HistoricoClinico;
import desafio.zoo.model.HistoricoEtologico;
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
    List<HistoricoClinico> historicoClinicoList;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        historicoClinico = HistoricoEtologico.findById(pId);
        return Response.ok(historicoClinico).status(200).build();
    }
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true")  Boolean ativo) {
        long historicoEtologico = HistoricoEtologico.count("isAtivo = ?1", ativo);
        return Response.ok(historicoEtologico).status(200).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") String sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize,
                               @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {
        PanacheQuery<HistoricoClinico> historicoClinico;
        if(sortQuery.equals("desc")) {
            historicoClinico = HistoricoClinico.find("isAtivo =?1 order by id desc", ativo);
            return Response.ok(historicoClinico.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
        }else {
            historicoClinico = HistoricoClinico.find("isAtivo =?1 order by id asc", ativo);
            return Response.ok(historicoClinico.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
        }
    }
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addHistoricoClinico(pHistoricoClinico, email);
        return Response.ok().status(201).build();
    }
    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(HistoricoClinico pHistoricoClinico, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateHistoricoClinico(pHistoricoClinico, email);
        return Response.ok().status(200).build();
    }
    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteHistoricoClinico(pListIdHistoricoClinico, email);
        return Response.ok().status(200).build();
    }
    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListIdHistoricoClinico, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateHistoricoClinico(pListIdHistoricoClinico, email);
        return Response.ok().status(200).build();
    }
}

