package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Profile;
import desafio.zoo.repository.ProfileRepository;
import desafio.zoo.utils.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
@ApplicationScoped
public class ProfileController {

    @ConfigProperty(name = "quarkus.http.body.uploads-directory")
    String directory;

    @Inject
    ProfileRepository repository;

    public List<Profile> listUploads() {
        return repository.listAll();
    }

    public Optional<Profile> findOne(Long id) {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new RuntimeException("File not found");
        }

        return profileOp;
    }

    @Transactional
    public Profile sendUpload(@NotNull FormData data, String pFileRefence, Long pIdAnimal) throws IOException {

        List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/png", "application/pdf", "document/doc", "document/docx", "application/zip", "application/vnd.sealed.xls");

        if (!mimetype.contains(data.getFile().contentType())) {
            throw new IOException("Tipo de arquivo não suportado.");
        }

        if (data.getFile().size() > 1024 * 1024 * 4) {
            throw new IOException("Arquivo muito grande.");
        }

        Profile profile = new Profile();

        String fileName = UUID.randomUUID() + "-" + data.getFile().fileName();

        profile.originalName = data.getFile().fileName();

        profile.keyName = fileName;

        profile.mimetype = data.getFile().contentType();

        profile.fileSize = data.getFile().size();

        profile.dataCriado = new Date();

        profile.animal = Animal.findById(pIdAnimal);

        profile.fileReference = pFileRefence;

        repository.persist(profile);

        Files.copy(data.getFile().filePath(), Paths.get(directory + fileName));

        return profile;
    }

    @Transactional
    public void removeUpload(Long id) throws IOException {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new IOException("Arquivo não encontrado.");
        }

        Profile profile = profileOp.get();

        repository.delete(profile);

        Files.deleteIfExists(Paths.get(directory + profile.keyName));
    }
}
