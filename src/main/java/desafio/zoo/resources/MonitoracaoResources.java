package desafio.zoo.resources;

import desafio.zoo.controller.MonitoracaoController;
import desafio.zoo.model.Monitoracao;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/monitoracao")
public class MonitoracaoResources {

    @Inject
    MonitoracaoController controller;
    Monitoracao monitoracao;
    List<Monitoracao> monitoracaoList;
    Page page;


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Monitoracao pMonitoracao) {
        monitoracao = controller.getMonitoracao(pMonitoracao);
        return Response.ok(monitoracao).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        monitoracaoList = controller.getMonitoracaoListAtivos();
        return Response.ok(monitoracaoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        monitoracaoList = controller.getMonitoracaoListInativos();
        return Response.ok(monitoracaoList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Monitoracao pMonitoracao) {
        controller.addMonitoracao(pMonitoracao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Monitoracao pMonitoracao) {
        controller.updateMonitoracao(pMonitoracao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Monitoracao pMonitoracao) {
        controller.deleteMonitoracao(pMonitoracao);
        return Response.ok().status(200).build();
    }
}
