package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoEtologicoController;
import desafio.zoo.model.HistoricoEtologico;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/historicoEtologico")
public class HistoricoEtologicoResources {

    @Inject
    HistoricoEtologicoController controller;
    HistoricoEtologico historicoEtologico;
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getHistoricoEtologicoById(@PathParam("id") Long pId) {
        historicoEtologico = PanacheEntityBase.findById(pId);
        return Response.ok(historicoEtologico).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<HistoricoEtologico> historicoEtologico =  HistoricoEtologico.find("isAtivo", true);
        return Response.ok(historicoEtologico.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @GET
    @Path("/inativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<HistoricoEtologico> historicoEtologico =  HistoricoEtologico.find("isAtivo", false);
        return Response.ok(historicoEtologico.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(HistoricoEtologico pHistoricoEtologico) {
        controller.addHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(HistoricoEtologico pHistoricoEtologico) {
        controller.updateHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<HistoricoEtologico> historicoEtologicoList) {
        controller.deleteHistoricoEtologico(historicoEtologicoList);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<HistoricoEtologico> historicoEtologicoList) {
        controller.reactivateHistoricoEtologico(historicoEtologicoList);
        return Response.ok().status(200).build();
    }
}