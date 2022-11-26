package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Nutricao;
import desafio.zoo.model.Profile;
import desafio.zoo.model.Responses;
import desafio.zoo.repository.ProfileRepository;
import desafio.zoo.utils.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    ProfileRepository repository;

    Responses responses;

    public List<Profile> listUploads() {
        return repository.listAll();
    }

    public Profile findOne(Long id) {

        Profile profile = Profile.findById(id);

        if (profile == null) {
            throw new RuntimeException("File not found");
        }

        return profile;
    }

    public Response sendUpload(@NotNull FormData data, String pFileRefence, Long pIdAnimal) throws IOException {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        String originalName = data.getFile().fileName();

        Profile profileCheck = Profile.find("originalname = ?1 and filereference =?2 and animalid = ?3", originalName, pFileRefence, pIdAnimal).firstResult();

        if (profileCheck == null) {
            Profile profile = new Profile();
            List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "application/msword", "application/vnd.ms-excel", "application/xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/gif", "image/png", "text/plain", "application/vnd.ms-powerpoint", "application/pdf", "text/csv", "document/doc", "document/docx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/zip", "application/vnd.sealed.xls");

            if (!mimetype.contains(data.getFile().contentType())) {
                throw new IOException("Tipo de arquivo não suportado. Aceito somente arquivos nos formatos: ppt, pptx csv, doc, docx, txt, pdf, xlsx, xml, xls, jpg, jpeg, png e zip.");
            }

            if (data.getFile().size() > 1024 * 1024 * 4) {
                throw new IOException("Arquivo muito grande.");
            }

            String fileName = pFileRefence + "-" + pIdAnimal + "-" + data.getFile().fileName();

            profile.originalName = data.getFile().fileName();

            profile.keyName = fileName;

            profile.mimetype = data.getFile().contentType();

            profile.fileSize = data.getFile().size();

            profile.dataCriado = new Date();

            profile.animal = Animal.findById(pIdAnimal);

            profile.nomeAnimal = profile.animal.nomeApelido;

            profile.fileReference = pFileRefence;

            profile.isAtivo = Boolean.TRUE;

            profile.persist();

            Files.copy(data.getFile().filePath(), Paths.get(directory + fileName));
            responses.status = 200;
            responses.messages.add("Arquivo adicionado com sucesso!");
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } else {
            responses.status = 500;
            responses.messages.add("Já existe um arquivo com o mesmo nome. Verifique!");
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
