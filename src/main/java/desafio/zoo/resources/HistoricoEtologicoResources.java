package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoEtologicoController;
import desafio.zoo.model.HistoricoEtologico;
import io.quarkus.panache.common.Page;

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
    List<HistoricoEtologico> historicoEtologicoList;
    Page page;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(HistoricoEtologico pHistoricoEtologico) {
        historicoEtologico = controller.getHistoricoEtologico(pHistoricoEtologico);
        return Response.ok(historicoEtologico).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        historicoEtologicoList = controller.getHistoricoEtologicoListAtivos();
        return Response.ok(historicoEtologicoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        historicoEtologicoList = controller.getHistoricoEtologicoListInativos();
        return Response.ok(historicoEtologicoList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(HistoricoEtologico pHistoricoEtologico) {
        controller.addHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(HistoricoEtologico pHistoricoEtologico) {
        controller.updateHistoricoEtologico(pHistoricoEtologico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response deleteList(List<HistoricoEtologico> historicoEtologicoList, @QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.deleteHistoricoEtologico(historicoEtologicoList);
        return Response.ok().status(200).build();
    }
}