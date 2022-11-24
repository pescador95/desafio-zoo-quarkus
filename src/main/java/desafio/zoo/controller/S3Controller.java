package desafio.zoo.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Profile;
import desafio.zoo.repository.ProfileRepository;
import desafio.zoo.utils.FileObject;
import desafio.zoo.utils.FormData;
import desafio.zoo.utils.ProfileS3;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jetbrains.annotations.NotNull;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ApplicationScoped
public class S3Controller {

    @ConfigProperty(name = "bucket.name")
    String bucketName;

    @Inject
    ProfileRepository repository;

    @Inject
    S3Client s3;

    public ProfileS3 listS3() {
        List<Profile> profiles = repository.listAll();

        ListObjectsRequest listRequest = ListObjectsRequest.builder().bucket(bucketName).build();

        List<FileObject> listObjectsRequest = s3.listObjects(listRequest).contents().stream()
                .map(FileObject::from)
                .collect(Collectors.toList());

        ProfileS3 profileS3View = new ProfileS3(profiles, listObjectsRequest);

        return profileS3View;
    }

    public ProfileS3 findOne(Long id) {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new RuntimeException("Arquivo não encontrado.");
        }

        Profile profile = profileOp.get();

        ListObjectsRequest listRequest = ListObjectsRequest.builder().bucket(bucketName).build();

        List<FileObject> listObjectsRequest = s3.listObjects(listRequest).contents().stream()
                .map(FileObject::from)
                .filter(item -> item.getObjectKey().equals(profile.keyName))
                .collect(Collectors.toList());

        ProfileS3 profileS3View = new ProfileS3(profile, listObjectsRequest.get(0));

        return profileS3View;
    }

    @Transactional
    public Profile sendS3(@NotNull FormData data, String pFileRefence, Long pIdAnimal) {

        List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/png", "application/pdf", "document/doc", "document/docx", "application/zip", "application/vnd.sealed.xls");

        if (!mimetype.contains(data.getFile().contentType())) {
            throw new RuntimeException("Arquivo não suportado.");
        }

        if (data.getFile().size() > 1024 * 1024 * 4) {
            throw new RuntimeException("Arquivo muito grande.");
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

        PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName)
                .key(fileName)
                .contentType(data.getFile().contentType())
                .build();

        s3.putObject(objectRequest, RequestBody.fromFile(data.getFile().filePath()));

        return profile;
    }

    @Transactional
    public void removeS3(Long id) {

        Profile profile = Profile.findById(id);

        if (profile == null) {
            throw new RuntimeException("Arquivo não encontrado.");
        }

        Profile.delete("id = ?1", profile.id);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName)
                .key(profile.keyName).build();

        s3.deleteObject(deleteObjectRequest);
    }
}