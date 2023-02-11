package desafio.zoo.resources;

import desafio.zoo.controller.ProfileController;
import desafio.zoo.model.Profile;
import desafio.zoo.model.Responses;
import desafio.zoo.utils.FormData;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Path("uploads")
@Produces({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})

@Transactional
public class ProfileResources {

    @Inject
    ProfileController controller;
    Responses responses;

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response count(@QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgFilter") @DefaultValue("") String strgFilter) {
        String query = "id > 0 " + strgFilter;
        long uploads = Profile.count(query);
        return Response.ok(uploads).status(Response.Status.ACCEPTED).build();
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response listUploads(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                                @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                                @QueryParam("animalAtivo") @DefaultValue("true") Boolean animalAtivo,
                                @QueryParam("page") @DefaultValue("0") int pageIndex,
                                @QueryParam("size") @DefaultValue("10") int pageSize,
                                @QueryParam("id") @DefaultValue("0") int id,
                                @QueryParam("strgFilter") @DefaultValue("") String strgFilter,
                                @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        String query = "id > " + "0" + " " + strgFilter + " " + "order by " + strgOrder + " " + sortQuery;
        PanacheQuery<Profile> profile;
        profile = Profile.find(query);

        return Response.ok(profile.page(Page.of(pageIndex, pageSize)).list().stream().filter(c -> c.animal.isAtivo = animalAtivo).collect(Collectors.toList())).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
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
    @Produces({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public Response sendUpload(@MultipartForm("file") FormData file,
                               @QueryParam("fileReference") String fileReference,
                               @QueryParam("idAnimal") Long idAnimal) {
        try {
            return controller.sendUpload(file, fileReference, idAnimal);
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({ "veterinario", "biologo", "dev" })
    public Response removeUpload(List<Long> pListIdProfile) {

        try {
            return controller.removeUpload(pListIdProfile);
        } catch (Exception e) {
            responses = new Responses();
            responses.status = 500;
            if (pListIdProfile.size() <= 1) {
                responses.messages.add("Não foi possível excluir o Arquivo.");
            } else {
                responses.messages.add("Não foi possível excluir os Arquivos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}