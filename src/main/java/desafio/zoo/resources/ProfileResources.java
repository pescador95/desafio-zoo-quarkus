package desafio.zoo.resources;

import desafio.zoo.controller.ProfileController;
import desafio.zoo.model.Profile;
import desafio.zoo.model.Responses;
import desafio.zoo.utils.FormData;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("uploads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Transactional
public class ProfileResources {

    @Inject
    ProfileController controller;
    Responses responses;
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
            Profile profile = controller.findOne(id);


            return Response.ok(profile).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/")
    public Response sendUpload(@MultipartForm FormData pData, @QueryParam("fileRefence") String pFileRefence,
            @QueryParam("idAnimal") Long pIdAnimal) {

        try {
            return controller.sendUpload(pData, pFileRefence, pIdAnimal);
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeUpload(@PathParam("id") List<Long> pListIdProfile) {

        try {
            return controller.removeUpload(pListIdProfile);
        } catch (Exception e) {
            if (pListIdProfile.size() <= 1) {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir o Arquivo.");
            } else {
                responses = new Responses();
                responses.status = 500;
                responses.messages.add("Não foi possível excluir os Arquivos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
