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
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        enriquecimentoAmbiental = EnriquecimentoAmbiental.findById(pId);
        return Response.ok(enriquecimentoAmbiental).status(200).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long enriquecimentoAmbiental = EnriquecimentoAmbiental.count(query);
        return Response.ok(enriquecimentoAmbiental).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response list(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                         @QueryParam("page") @DefaultValue("0") int pageIndex,
                         @QueryParam("size") @DefaultValue("20") int pageSize,
                         @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                         @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
                         @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {
        String query = "isAtivo = " + ativo + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<EnriquecimentoAmbiental> enriquecimentoAmbiental;
        enriquecimentoAmbiental = EnriquecimentoAmbiental.find(query);
        return Response.ok(enriquecimentoAmbiental.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response add(EnriquecimentoAmbiental pEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Enriquecimento Ambiental cadastrado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        return Response.ok(responses).status(201, "Enriquecimento Ambiental cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(EnriquecimentoAmbiental pEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Enriquecimento Ambiental atualizado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateEnriquecimentoAmbiental(pEnriquecimentoAmbiental, email);
        return Response.ok(responses).status(200, "Enriquecimento Ambiental atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Integer countList = pListEnriquecimentoAmbiental.size();
        responses = new Responses();
        if(pListEnriquecimentoAmbiental.size() <= 1){
            responses.message = "Enriquecimento Ambiental excluído com sucesso!";
        } else {
            responses.message = countList + " Enriquecimentos Ambientais exclúidos com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        return Response.ok(responses).status(200, "Enriquecimento Ambiental excluído com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListEnriquecimentoAmbiental, @Context @NotNull SecurityContext context) {
        Integer countList = pListEnriquecimentoAmbiental.size();
        responses = new Responses();
        if(pListEnriquecimentoAmbiental.size() <= 1){
            responses.message = "Enriquecimento Ambiental recuperado com sucesso!";
        } else {
            responses.message = countList + " Enriquecimentos Ambientais recuperados com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateEnriquecimentoAmbiental(pListEnriquecimentoAmbiental, email);
        return Response.ok(responses).status(200,"Enriquecimento Ambiental recuperado com sucesso!").build();
    }
}