package project.services.users;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;


public interface StorageService {

    String store(MultipartFile multipartFile, Authentication authentication);

    Optional<File> load(String filename);

    List<String> getAllImages();

    String getPath();

    File get404();
}
