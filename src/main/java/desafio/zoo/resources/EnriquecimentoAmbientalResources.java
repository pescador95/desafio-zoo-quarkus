package desafio.zoo.resources;

import desafio.zoo.controller.EnriquecimentoAmbientalController;
import desafio.zoo.model.EnriquecimentoAmbiental;
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

@Path("/enriquecimentoAmbiental")
public class EnriquecimentoAmbientalResources {

    @Inject
    EnriquecimentoAmbientalController controller;
    EnriquecimentoAmbiental enriquecimentoAmbiental;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getById(@PathParam("id") Long pId) {
        enriquecimentoAmbiental = EnriquecimentoAmbiental.findById(pId);
        return Response.ok(enriquecimentoAmbiental).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long enriquecimentoAmbiental = EnriquecimentoAmbiental.count(query);
        return Response.ok(enriquecimentoAmbiental).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("animalAtivo") @DefaultValue("true") Boolean animalAtivo,
            @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<EnriquecimentoAmbiental> enriquecimentoAmbiental;
        enriquecimentoAmbiental = EnriquecimentoAmbiental.find(query);
        return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex, pageSize)).list().stream().filter(c -> c.animal.isAtivo = animalAtivo).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response add(EnriquecimentoAmbiental pEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.addEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível cadastrar o Enriquecimento Ambiental.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(EnriquecimentoAmbiental pEnriquecimentoAmbiental,
            @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.updateEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            responses.messages.add("Não foi possível atualizar o Enriquecimento Ambiental.");
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.deleteEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListEnriquecimentoAmbiental.size() <= 1) {
                responses.messages.add("Não foi possível excluir o Enriquecimento Ambiental.");
            } else {
                responses.messages.add("Não foi possível excluir os Enriquecimentos Ambientais.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivateList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        try {
            Principal json = context.getUserPrincipal();
            String email = json.getName();
            return controller.reactivateEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListEnriquecimentoAmbiental.size() <= 1) {
                responses.messages.add("Não foi possível excluir o Enriquecimento Ambiental.");
            } else {
                responses.messages.add("Não foi possível excluir os Enriquecimentos Ambientais.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}