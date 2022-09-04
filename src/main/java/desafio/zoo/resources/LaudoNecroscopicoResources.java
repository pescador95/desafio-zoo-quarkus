package desafio.zoo.resources;

import desafio.zoo.controller.LaudoNecroscopicoController;
import desafio.zoo.model.LaudoNecroscopico;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/laudoNecroscopico")
public class LaudoNecroscopicoResources {

    @Inject
    LaudoNecroscopicoController controller;
    LaudoNecroscopico laudonecroscopico;
    List<LaudoNecroscopico> laudoNecroscopicoList;
    Page page;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(LaudoNecroscopico pLaudoNecroscopico) {
        laudonecroscopico = controller.getLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok(laudonecroscopico).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        laudoNecroscopicoList = controller.getLaudoNecroscopicoListAtivos();
        return Response.ok(laudoNecroscopicoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        laudoNecroscopicoList = controller.getLaudoNecroscopicoListInativos();
        return Response.ok(laudoNecroscopicoList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(LaudoNecroscopico pLaudoNecroscopico) {
        controller.addLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(LaudoNecroscopico pLaudoNecroscopico) {
        controller.updateLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(LaudoNecroscopico pLaudoNecroscopico) {
        controller.deleteLaudoNecroscopico(pLaudoNecroscopico);
        return Response.ok().status(200).build();
    }
}
