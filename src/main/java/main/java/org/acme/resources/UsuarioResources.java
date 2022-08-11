package main.java.org.acme.resources;

import main.java.org.acme.controller.UsuarioController;
import main.java.org.acme.model.Usuario;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioResources {

    @Inject
    UsuarioController controller;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Usuario pUsuario) {
        Usuario usuario = controller.getUser(pUsuario);
        return Response.ok(usuario).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Usuario pUsuario) {
        controller.addUser(pUsuario);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Usuario pUsuario) {
        controller.updateUser(pUsuario);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Usuario pUsuario) {
        controller.deleteUser(pUsuario);
        return Response.ok().status(200).build();
    }
}
