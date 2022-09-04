package desafio.zoo.resources;

import desafio.zoo.controller.InternamentoController;
import desafio.zoo.model.Internamento;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/Internamento")
public class InternamentoResources {

    @Inject
    InternamentoController controller;
    Internamento Internamento;
    List<Internamento> internamentoList;
    Page page;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Internamento pInternamento) {
        Internamento = controller.getInternamento(pInternamento);
        return Response.ok(Internamento).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        internamentoList = controller.getInternamentoListAtivos();
        return Response.ok(internamentoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        internamentoList = controller.getInternamentoListInativos();
        return Response.ok(internamentoList).status(200).build();
    }


    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Internamento pInternamento) {
        controller.addInternamento(pInternamento);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Internamento pInternamento) {
        controller.updateInternamento(pInternamento);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Internamento pInternamento) {
        controller.deleteInternamento(pInternamento);
        return Response.ok().status(200).build();
    }
}
