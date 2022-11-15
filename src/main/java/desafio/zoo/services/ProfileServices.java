package desafio.zoo.services;

import desafio.zoo.model.Profile;
import desafio.zoo.repository.ProfileRepository;
import desafio.zoo.utils.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProfileServices {

    @ConfigProperty(name = "quarkus.http.body.uploads-directory")
    String directory;
    @Inject
    ProfileRepository repository;

    public Profile sendUpload(FormData data) throws IOException {

        List<String> mimeType = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif", "document/pdf", "document/doc", "document/docx", "document/xls", "document/xlsx");

        if (!mimeType.contains(data.getFile().contentType())) {

            throw new IOException("Arquivo não suportado");
        }
        if (data.getFile().size() > 1024 * 1024 * 4){
            throw new IOException("Arquivo muito grande.");
        }
        Profile profile = new Profile();

        String fileName = UUID.randomUUID() + "-" + data.getFile().fileName();

        profile.setOriginalName(data.getFile().fileName());

        profile.setKeyName(fileName);

        profile.setMimetype(data.getFile().contentType());

        profile.setFileSize(data.getFile().size());

        profile.setDataCriada(new Date());

        repository.persist(profile);

        Files.copy(data.getFile().filePath(), Paths.get(directory + fileName));
        return profile;
    }
    @Transactional
    public void removeUpload(Long id) throws IOException {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new IOException("Arquivo não encontrado");
        }

        Profile profile = profileOp.get();

        repository.delete(profile);

        Files.deleteIfExists(Paths.get(directory + profile.getKeyName()));
    }
}
