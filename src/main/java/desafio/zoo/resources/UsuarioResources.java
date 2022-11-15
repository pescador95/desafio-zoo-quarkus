package desafio.zoo.resources;

import desafio.zoo.controller.UsuarioController;
import desafio.zoo.model.Responses;
import desafio.zoo.model.Usuario;
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

@Path("/usuario")
public class UsuarioResources {

    @Inject
    UsuarioController controller;
    Usuario usuario;

    Responses responses;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response getById(@PathParam("id") Long pId) {
        usuario = Usuario.findById(pId);
        return Response.ok(usuario).status(200).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long usuario = Usuario.count(query);
        return Response.ok(usuario).status(200).build();
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
        String query = "isAtivo = " + ativo + " " + strgFilter + " order by "+ strgOrder + " " + sortQuery;
        PanacheQuery<Usuario> usuario;
        usuario = Usuario.find(query);
        return Response.ok(usuario.page(Page.of(pageIndex, pageSize)).list()).status(200).build();
    }

    @GET
    @Path("/myprofile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})

    public Response getmyprofile(Usuario pUsuario, @Context @NotNull SecurityContext context) {
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        Usuario usuario = Usuario.find("email = ?1", email).firstResult();
        return Response.ok(usuario).status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})

    public Response add(Usuario pUsuario, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.status = 201;
        responses.message = "Usuário cadastrado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.addUser(pUsuario, email);
        return Response.ok(responses).status(201, "Usuário cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response update(Usuario pUsuario, @Context @NotNull SecurityContext context) {
        responses = new Responses();
        responses.status = 200;
        responses.message = "Usuário atualizado com sucesso!";
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.updateUser(pUsuario, email);
        return Response.ok(responses).status(200,"Usuário atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response deleteList(List<Long> pListIdusuario, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdusuario.size();
        responses = new Responses();
        responses.status = 200;
        if(pListIdusuario.size() <= 1){
            responses.message = "Usuário exclúido com sucesso!";
        } else {
            responses.message = countList + " Usuários exclúidos com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.deleteUser(pListIdusuario, email);
        return Response.ok(responses).status(200,"Usuário excluído com sucesso!").build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"veterinario", "biologo", "dev"})
    public Response reactivateList(List<Long> pListIdusuario, @Context @NotNull SecurityContext context) {
        Integer countList = pListIdusuario.size();
        responses = new Responses();
        responses.status = 200;
         if(pListIdusuario.size() <= 1){
            responses.message = "Usuário recuperado com sucesso!";
        } else {
            responses.message = countList + " Usuários recuperados com sucesso!";
        }
        Principal json = context.getUserPrincipal();
        String email = json.getName();
        controller.reactivateUser(pListIdusuario, email);
        return Response.ok(responses).status(200, "Usuário recuperado com sucesso!").build();
    }
}