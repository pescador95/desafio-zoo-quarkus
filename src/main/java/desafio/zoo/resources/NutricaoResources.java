package desafio.zoo.resources;

import desafio.zoo.controller.NutricaoController;
import desafio.zoo.model.Nutricao;
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


@Path("/nutricao")
public class NutricaoResources {

    @Inject
    NutricaoController controller;
    Nutricao nutricao;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        nutricao = Nutricao.findById(pId);
        return Response.ok(nutricao).status(200).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long nutricao = Nutricao.count(query);
        return Response.ok(nutricao).status(200).build();
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
        PanacheQuery<Nutricao> nutricao;
        nutricao = Nutricao.find(query);
        return Response.ok(nutricao.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response add(Nutricao pNutricao, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Ficha de Nutrição cadastrada com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addNutricao(pNutricao, email);
        return Response.ok(responses).status(201, "Ficha de Nutrição cadastrada com sucesso!").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Nutricao pNutricao, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.message = "Ficha de Nutrição atualizada com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateNutricao(pNutricao, email);
        return Response.ok(responses).status(200, "Ficha de Nutrição atualizada com sucesso!").build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListIdnutricao, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdnutricao.size();
        responses = new Responses();
        if(pListIdnutricao.size() <= 1){
            responses.message = "Ficha de Nutrição excluída com sucesso!";
        } else {
            responses.message = countList + " Fichas de Nutrição exclúidas com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteNutricao(pListIdnutricao, email);
        return Response.ok(responses).status(200, "Ficha de Nutrição excluída com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListIdnutricao, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdnutricao.size();
        responses = new Responses();
        if(pListIdnutricao.size() <= 1){
            responses.message = "Ficha de Nutrição recuperada com sucesso!";
        } else {
            responses.message = countList + " Fichas de Nutrição recuperadas com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateNutricao(pListIdnutricao, email);
        return Response.ok(responses).status(200, "Ficha de Nutrição recuperada com sucesso!").build();
    }
}