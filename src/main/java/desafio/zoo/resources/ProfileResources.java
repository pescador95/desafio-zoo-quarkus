package desafio.zoo.resources;

import desafio.zoo.model.Profile;
import desafio.zoo.controller.ProfileController;
import desafio.zoo.utils.FormData;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Path("uploads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ProfileResources {

    @Inject
    ProfileController controller;

    @GET
    @Path("/")
    public Response listUploads() {

        List<Profile> profiles = controller.listUploads();

        return Response.ok(profiles).build();
    }

    @GET
    @Path("{id}")
    public Response findOne(@PathParam("id") Long id) {

        try {
            Optional<Profile> profile = controller.findOne(id);

            return Response.ok(profile).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }

    @POST
    @Path("/")
    public Response sendUpload(@MultipartForm FormData pData, @QueryParam("fileRefence") String pFileRefence,
                               @QueryParam("idAnimal") Long pIdAnimal) {

        try {
            Profile profile = controller.sendUpload(pData, pFileRefence, pIdAnimal);

            return Response.ok(profile).status(201).build();
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(401).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeUpload(@PathParam("id") Long id) {

        try {
            controller.removeUpload(id);

            return Response.status(204).build();
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }
}
