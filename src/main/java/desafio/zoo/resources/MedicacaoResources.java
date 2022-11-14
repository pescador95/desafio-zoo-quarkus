package desafio.zoo.resources;

import desafio.zoo.controller.MedicacaoController;
import desafio.zoo.model.Medicacao;
import desafio.zoo.model.Responses;
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

    Responses responses;

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
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long medicacao = Medicacao.count(query);
        return Response.ok(medicacao).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                         @QueryParam("page") @DefaultValue("0") int pageIndex,
                         @QueryParam("size") @DefaultValue("20") int pageSize,
                         @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                         @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
                         @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<Medicacao> medicacao;
        medicacao = Medicacao.find(query);
        return Response.ok(medicacao.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response add(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Medicação cadastrado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addMedicacao(pMedicacao, email);
        return Response.ok(responses).status(201,"Medicação cadastrada com sucesso!").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Medicação atualizado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateMedicacao(pMedicacao, email);
        return Response.ok(responses).status(200,"Medicação atualizada com sucesso!").build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        Integer countList = pListMedicacao.size();
        responses = new Responses();
        if(pListMedicacao.size() <= 1){
            responses.message = "Medicação excluída com sucesso!";
        } else {
            responses.message = countList + " Medicações exclúidas com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteMedicacao(pListMedicacao, email);
        return Response.ok(responses).status(200, "Medicação excluída com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        Integer countList = pListMedicacao.size();
        responses = new Responses();
        if(pListMedicacao.size() <= 1){
            responses.message = "Medicação recuperada com sucesso!";
        } else {
            responses.message = countList + " Medicações recuperadas com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateMedicacao(pListMedicacao, email);
        return Response.ok(responses).status(200, "Medicação recuperada com sucesso!").build();
    }
}