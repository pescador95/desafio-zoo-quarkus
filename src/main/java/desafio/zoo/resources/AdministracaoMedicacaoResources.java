package desafio.zoo.resources;

import desafio.zoo.controller.AdministracaoMedicacaoController;
import desafio.zoo.model.AdministracaoMedicacao;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/administracaoMedicacao")
public class AdministracaoMedicacaoResources {

    @Inject
    AdministracaoMedicacaoController controller;
    AdministracaoMedicacao administracaoMedicacao;
    List<AdministracaoMedicacao> administracaoMedicacaoList;
    Page page;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(AdministracaoMedicacao pAdministracaoMedicacao) {
        administracaoMedicacao = controller.getAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok(administracaoMedicacao).status(200).build();
    }

    @GET
    @Path("/getListAtivos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        administracaoMedicacaoList = controller.getAdministracaoMedicacaoListAtivos();
        return Response.ok(administracaoMedicacaoList).status(200).build();
    }

    @GET
    @Path("/getListInativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        administracaoMedicacaoList = controller.getAdministracaoMedicacaoListInativos();
        return Response.ok(administracaoMedicacaoList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(AdministracaoMedicacao pAdministracaoMedicacao) {
        controller.addAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(AdministracaoMedicacao pAdministracaoMedicacao) {
        controller.updateAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response deleteList(List<AdministracaoMedicacao> administracaoMedicacaoList, @QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.deleteAdministracaoMedicacao(administracaoMedicacaoList);
        return Response.ok().status(200).build();
    }
}