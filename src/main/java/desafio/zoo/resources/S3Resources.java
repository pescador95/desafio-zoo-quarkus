package desafio.zoo.resources;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import desafio.zoo.model.Profile;
import desafio.zoo.controller.S3Controller;
import desafio.zoo.utils.FormData;
import desafio.zoo.utils.ProfileS3;
import org.jboss.resteasy.reactive.MultipartForm;

@Path("s3")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class S3Resources {

    @Inject
    S3Controller service;

    @GET
    public Response listS3() {

        ProfileS3 objects = service.listS3();

        return Response.ok(objects).build();
    }

    @GET
    @Path("{id}")
    public Response findOne(@PathParam("id") Long id) {

        try {
            ProfileS3 profile = service.findOne(id);

            return Response.ok(profile).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }

    @POST
    public Response sendS3(@MultipartForm FormData data, @QueryParam("fileRefence") String pFileRefence, @QueryParam("idAnimal") Long pIdAnimal) {

        try {
            Profile profile = service.sendS3(data, pFileRefence, pIdAnimal);

            return Response.ok(profile).status(201).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(401).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeS3(@PathParam("id") Long id) {

        try {
            service.removeS3(id);

            return Response.status(204).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }
}