package desafio.zoo.resources;

import desafio.zoo.model.DownloadFormData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("downloads")

public class DownloadFormDataResources {

    @GET
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Path("{id}")
    public DownloadFormData getFileById(@PathParam("id") Long pId) {
        //IMPLEMENTAR DOWNLAOD DOS ARQUIVOS PELO ID DELE: https://quarkus.io/guides/resteasy-reactive#http-compression
       return null;
    }
}