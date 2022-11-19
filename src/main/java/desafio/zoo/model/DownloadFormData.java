package desafio.zoo.model;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class DownloadFormData {

    @RestForm
    String name;

    @RestForm
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    File file;
}