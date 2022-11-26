package desafio.zoo.resources;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import desafio.zoo.model.Profile;
import desafio.zoo.controller.ProfileS3Controller;
import desafio.zoo.model.Responses;
import desafio.zoo.utils.FormData;
import desafio.zoo.model.ProfileS3;
import org.jboss.resteasy.reactive.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("s3")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileS3Resources {

    @Inject
    ProfileS3Controller controller;

    Responses responses;

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "isAtivo = " + ativo + " " + strgFilter;
        long uploads = Profile.count(query);
        return Response.ok(uploads).status(Response.Status.ACCEPTED).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listS3() {

        ProfileS3 objects = controller.listS3();

        return Response.ok(objects).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response findOne(@PathParam("id") Long id) {

        try {
            ProfileS3 profile = controller.findOne(id);

            return Response.ok(profile).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }

    @POST
    public Response sendS3(@MultipartForm FormData pData, @QueryParam("fileRefence") String pFileRefence, @QueryParam("idAnimal") Long pIdAnimal) {
        try {
        return controller.sendS3(pData, pFileRefence, pIdAnimal);
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response removeS3(List<Long> pListIdProfile) {

        try {
            return controller.removeS3(pListIdProfile);
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