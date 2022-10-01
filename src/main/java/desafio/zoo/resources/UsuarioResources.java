package desafio.zoo.resources;

import desafio.zoo.controller.UsuarioController;
import desafio.zoo.model.Usuario;
import io.quarkus.panache.common.Page;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuario")
public class UsuarioResources {

    @Inject
    UsuarioController controller;
    Usuario usuario;
    List<Usuario> usuarioList;
    Page page;
    JsonWebToken jwt;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response getUserById(@PathParam("id") Long pId) {
        usuario = usuario.findById(pId);
        return Response.ok(usuario).status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listAtivos(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        usuarioList = controller.getUserListAtivos();
        return Response.ok(usuarioList).status(200).build();
    }

    @GET
    @Path("/inativos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listInativos(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        usuarioList = controller.getUserListInativos();
        return Response.ok(usuarioList).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })

    public Response add(Usuario pUsuario) {
        controller.addUser(pUsuario);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response update(Usuario pUsuario) {
        controller.updateUser(pUsuario);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response deleteList(List<Usuario> usuarioList, @QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.deleteUser(usuarioList);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response reactivate(List<Usuario> usuarioList, @QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {
        page = Page.of(pageIndex, pageSize);
        controller.reactivateUser(usuarioList);
        return Response.ok().status(200).build();
    }
}