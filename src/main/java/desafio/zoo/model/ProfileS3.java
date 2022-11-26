package desafio.zoo.model;

import desafio.zoo.utils.FileObject;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;

@RegisterForReflection
public class ProfileS3 {

    private List<Profile> profiles;

    private List<FileObject> fileObjects;

    private Profile profile;

    private FileObject fileObject;

    public ProfileS3(List<Profile> profiles, List<FileObject> fileObjects) {
        this.profiles = profiles;
        this.fileObjects = fileObjects;
    }

    public ProfileS3(Profile profile, FileObject fileObject) {
        this.profile = profile;
        this.fileObject = fileObject;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public List<FileObject> getFileObjects() {
        return fileObjects;
    }

    public FileObject getFileObject() {
        return fileObject;
    }

    public Profile getProfile() {
        return profile;
    }

}