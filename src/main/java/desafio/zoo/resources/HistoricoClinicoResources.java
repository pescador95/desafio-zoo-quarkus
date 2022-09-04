package desafio.zoo.resources;

import desafio.zoo.controller.HistoricoClinicoController;
import desafio.zoo.model.HistoricoClinico;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/historicoClinico")
public class HistoricoClinicoResources {

    @Inject
    HistoricoClinicoController controller;
    HistoricoClinico HistoricoClinico;
    List<HistoricoClinico> historicoClinicoList;
    Page page;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(HistoricoClinico pHistoricoClinico) {
        HistoricoClinico = controller.getHistoricoClinico(pHistoricoClinico);
        return Response.ok(HistoricoClinico).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        historicoClinicoList = controller.getHistoricoClinicoListAtivos();
        return Response.ok(historicoClinicoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        historicoClinicoList = controller.getHistoricoClinicoListInativos();
        return Response.ok(historicoClinicoList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(HistoricoClinico pHistoricoClinico) {
        controller.addHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(HistoricoClinico pHistoricoClinico) {
        controller.updateHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(HistoricoClinico pHistoricoClinico) {
        controller.deleteHistoricoClinico(pHistoricoClinico);
        return Response.ok().status(200).build();
    }
}