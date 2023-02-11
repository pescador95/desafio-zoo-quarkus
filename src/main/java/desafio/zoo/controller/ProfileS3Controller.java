package desafio.zoo.controller;

import desafio.zoo.model.Animal;
import desafio.zoo.model.Profile;
import desafio.zoo.model.ProfileS3;
import desafio.zoo.model.Responses;
import desafio.zoo.repository.ProfileRepository;
import desafio.zoo.utils.FileObject;
import desafio.zoo.utils.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ProfileS3Controller {

    public Profile profile;
    @ConfigProperty(name = "bucket.name")
    String bucketName;

    @Inject
    ProfileRepository repository;

    @Inject
    S3Client s3;

    Responses responses;

    public ProfileS3 listS3() {
        List<Profile> profiles = repository.listAll();

        ListObjectsRequest listRequest = ListObjectsRequest.builder().bucket(bucketName).build();

        List<FileObject> listObjectsRequest = s3.listObjects(listRequest).contents().stream()
                .map(FileObject::from)
                .collect(Collectors.toList());

        return new ProfileS3(profiles, listObjectsRequest);
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

        return new ProfileS3(profile, listObjectsRequest.get(0));
    }


    public Response sendS3(@NotNull FormData data, String pFileRefence, Long pIdAnimal) throws IOException {

        responses = new Responses();
        responses.messages = new ArrayList<>();

        String originalName = data.getFile().fileName();

        Profile profileCheck = Profile.find("originalname = ?1 and filereference =?2 and animalid = ?3", originalName, pFileRefence, pIdAnimal).firstResult();
        if (profileCheck == null) {
        Profile profile = new Profile();
        List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "application/msword", "application/vnd.ms-excel", "application/xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/gif", "image/png", "text/plain", "application/vnd.ms-powerpoint", "application/pdf", "text/csv", "document/doc", "document/docx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/zip", "application/vnd.sealed.xls");

        if (!mimetype.contains(data.getFile().contentType())) {
            throw new RuntimeException("Arquivo não suportado.");
        }

        if (data.getFile().size() > 1024 * 1024 * 4) {
            throw new RuntimeException("Arquivo muito grande.");
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


        profile.persist();

        PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName)
                .key(fileName)
                .contentType(data.getFile().contentType())
                .build();

        s3.putObject(objectRequest, RequestBody.fromFile(data.getFile().filePath()));

        responses.status = 200;
        responses.messages.add("Arquivo adicionado com sucesso!");
        return Response.ok(responses).status(Response.Status.ACCEPTED).build();
    } else {
        responses.status = 500;
        responses.messages.add("Já existe um arquivo com o mesmo nome. Verifique!");
    }
        return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
    }


    public Response removeS3(List<Long> pListIdProfile) {

        List<Profile> profileList;
        responses = new Responses();
        responses.messages = new ArrayList<>();

        profileList = Profile.list("id in ?1 and isAtivo = true", pListIdProfile);
        int countList = pListIdProfile.size();

        try {
            profileList.forEach((profile) -> {


                Profile.delete("id = ?1", profile.id);

                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName)
                        .key(profile.keyName).build();
                s3.deleteObject(deleteObjectRequest);

            });
            responses.status = 200;
            if (countList <= 1) {
                responses.messages.add("Arquivo excluído com sucesso!");
            } else {
                responses.messages.add(countList + " Arquivos excluídos com sucesso!");
            }
            return Response.ok(responses).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            responses.status = 500;
            if (countList <= 1) {
                responses.messages.add("Arquivo não localizado ou já excluído.");
            } else {
                responses.messages.add("Arquivos não localizados ou já excluídos.");
            }
            return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
        }
    }
}