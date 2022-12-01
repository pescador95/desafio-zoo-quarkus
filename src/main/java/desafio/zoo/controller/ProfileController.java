package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Profile;
import desafio.zoo.model.Responses;
import desafio.zoo.utils.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional
public class ProfileController {

    public Profile profile;
    @ConfigProperty(name = "quarkus.http.body.uploads-directory")
    String directory;

    Responses responses;

    public Profile findOne(Long id) {

        Profile profile = Profile.findById(id);

        if (profile == null) {
            throw new RuntimeException("File not found");
        }

        return profile;
    }

    public Response sendUpload(@NotNull FormData file, String fileRefence, Long idAnimal) throws IOException {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        String originalName = file.getFile().fileName();

        Profile profileCheck = Profile.find("originalname = ?1 and filereference =?2 and animalid = ?3", originalName, fileRefence, idAnimal).firstResult();

        Animal animal = Animal.findById(idAnimal);

        if (animal != null) {
            if (profileCheck == null) {
                Profile profile = new Profile();
                List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "application/msword", "application/vnd.ms-excel", "application/xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/gif", "image/png", "text/plain", "application/vnd.ms-powerpoint", "application/pdf", "text/csv", "document/doc", "document/docx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/zip", "application/vnd.sealed.xls");

                if (!mimetype.contains(file.getFile().contentType())) {
                    throw new IOException("Tipo de arquivo não suportado. Aceito somente arquivos nos formatos: ppt, pptx csv, doc, docx, txt, pdf, xlsx, xml, xls, jpg, jpeg, png e zip.");
                }

                if (file.getFile().size() > 1024 * 1024 * 4) {
                    throw new IOException("Arquivo muito grande.");
                }

                String fileName = fileRefence + "-" + idAnimal + "-" + file.getFile().fileName();

                profile.originalName = file.getFile().fileName();

                profile.keyName = fileName;

                profile.mimetype = file.getFile().contentType();

                profile.fileSize = file.getFile().size();

                profile.dataCriado = new Date();

                profile.animal = animal;

                profile.nomeAnimal = profile.animal.nomeApelido;

                profile.fileReference = fileRefence;


                profile.persist();

                Files.copy(file.getFile().filePath(), Paths.get(directory + fileName));
                responses.status = 200;
                responses.messages.add("Arquivo adicionado com sucesso!");
                return Response.ok(responses).status(Response.Status.ACCEPTED).build();
            } else {
                responses.status = 500;
                responses.messages.add("Já existe um arquivo com o mesmo nome. Verifique!");
            }
        } else {
            responses.status = 500;
            responses.messages.add("Por favor, verifique o Animal do anexo.");
        }
        return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
    }


    public Response removeUpload(List<Long> pListIdProfile) {

        Integer countList = pListIdProfile.size();
        responses = new Responses();
        responses.messages = new ArrayList<>();

        try {
            pListIdProfile.forEach((pProfile) -> {
                profile = Profile.find("id = ?1 and isAtivo = true ORDER BY id DESC", pProfile).firstResult();

                try {
                    Profile.delete("id = ?1", profile.id);
                    Files.deleteIfExists(Paths.get(directory + profile.keyName));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            if (pListIdProfile.size() <= 1) {
                responses.status = 200;
                responses.messages.add("Arquivo excluído com sucesso!");
            } else {
                responses.status = 200;
                responses.messages.add(countList + " Arquivos excluídos com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (pListIdProfile.size() <= 1) {
                responses.status = 500;
                responses.messages.add("Arquivo não localizado ou já excluído.");
            } else {
                responses.status = 500;
                responses.messages.add("Arquivos não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}
