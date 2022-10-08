package desafio.zoo.resources;

import desafio.zoo.controller.MedicacaoController;
import desafio.zoo.model.Medicacao;
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


@Path("/medicacao")
public class MedicacaoResources {

    @Inject
    MedicacaoController controller;
    Medicacao medicacao;
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        medicacao = Medicacao.findById(pId);
        return Response.ok(medicacao).status(200).build();
    }
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true")  Boolean ativo) {
        long medicacao = Medicacao.count("isAtivo = ?1", ativo);
        return Response.ok(medicacao).status(200).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response list(@QueryParam("sort") String sortQuery,
                               @QueryParam("page") @DefaultValue("0") int pageIndex,
                               @QueryParam("size") @DefaultValue("20") int pageSize,
                               @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {
        PanacheQuery<Medicacao> medicacao;
        if(sortQuery.equals("desc")) {
            medicacao = Medicacao.find("isAtivo = ?1 order by id desc", ativo);
        }else{
            medicacao = Medicacao.find("isAtivo = ?1 order by id asc", ativo);
        }
        return Response.ok(medicacao.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addMedicacao(pMedicacao, email);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateMedicacao(pMedicacao, email);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteMedicacao(pListMedicacao, email);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateMedicacao(pListMedicacao, email);
        return Response.ok().status(200).build();
    }
}