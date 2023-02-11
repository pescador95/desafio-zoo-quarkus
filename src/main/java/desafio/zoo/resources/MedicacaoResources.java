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
import java.util.stream.Collectors;

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
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        medicacao = Medicacao.findById(pId);
        return Response.ok(medicacao).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long medicacao = Medicacao.count(query);
        return Response.ok(medicacao).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("animalAtivo") @DefaultValue("true") Boolean animalAtivo,
            @QueryParam("hcAtivo") @DefaultValue("true") Boolean hcAtivo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<Medicacao> medicacao;
        medicacao = Medicacao.find(query);
        return Response.ok(medicacao.page(Page.of(pageIndex, pageSize)).list().stream().filter(c -> (c.historicoClinico.isAtivo = hcAtivo) && (c.historicoClinico.animal.isAtivo = animalAtivo)).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addMedicacao(pMedicacao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível cadastrar a Medicação.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Medicacao pMedicacao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateMedicacao(pMedicacao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar a medicação.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteMedicacao(pListMedicacao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListMedicacao.size() <= 1) {
                responses.messages.add("Não foi possível excluir a medicação.");
            } else {
                responses.messages.add("Não foi possível excluir as medicações.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListMedicacao, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateMedicacao(pListMedicacao, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListMedicacao.size() <= 1) {
                responses.messages.add("Não foi possível reativar a medicação.");
            } else {
                responses.messages.add("Não foi possível reativar as medicações.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}