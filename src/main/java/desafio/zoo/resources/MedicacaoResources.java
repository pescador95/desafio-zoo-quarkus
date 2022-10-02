package desafio.zoo.resources;

import desafio.zoo.controller.MedicacaoController;
import desafio.zoo.model.Medicacao;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/medicacao")
public class MedicacaoResources {

    @Inject
    MedicacaoController controller;
    Medicacao medicacao;
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getMedicacaoById(@PathParam("id") Long pId) {
        medicacao = PanacheEntityBase.findById(pId);
        return Response.ok(medicacao).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<Medicacao> medicacao =  Medicacao.find("isAtivo", true);
        return Response.ok(medicacao.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @GET
    @Path("/inativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
                                 @QueryParam("page") @DefaultValue("0") int pageIndex,
                                 @QueryParam("size") @DefaultValue("20") int pageSize) {
        PanacheQuery<Medicacao> medicacao =  Medicacao.find("isAtivo", false);
        return Response.ok(medicacao.page(Page.of(pageIndex,pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(Medicacao pMedicacao) {
        controller.addMedicacao(pMedicacao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Medicacao pMedicacao) {
        controller.updateMedicacao(pMedicacao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Medicacao> medicacaoList) {
        controller.deleteMedicacao(medicacaoList);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Medicacao> medicacaoList) {
        controller.reactivateMedicacao(medicacaoList);
        return Response.ok().status(200).build();
    }
}