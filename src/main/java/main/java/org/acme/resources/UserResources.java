package main.java.org.acme.resources;

import main.java.org.acme.controller.UserController;
import main.java.org.acme.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResources {

    @Inject
     UserController controller;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(User pUser) {
        controller.getUser(pUser);
        return Response.ok().status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(User pUser) {
        controller.addUser(pUser);
        return Response.ok().status(200).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(User pUser) {
        controller.updateUser(pUser);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(User pUser) {
        controller.deleteUser(pUser);
        return Response.ok().status(200).build();
    }
}
