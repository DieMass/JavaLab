package project.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.models.user.Image;
import project.repositories.users.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class StorageServiceImpl implements StorageService {

    private final String path = System.getProperty("os.name").toLowerCase().startsWith("win") ?
            "D:\\Projects\\Java\\Education\\JavaLab\\2к2с\\DataSet\\" :
            "/mnt/3E66C61266C5CB3B/Projects/Java/Education/JavaLab/2к2с/DataSet/";

    @Autowired
    @Qualifier("imageRepositoryImpl")
    private ImageRepository imageRepository;
    private Random random = new Random();

    @Override
    public String store(MultipartFile multipartFile, Authentication authentication) {
        try {
            String name;
            do {
                name = "" + random.nextInt() + multipartFile.hashCode() + random.nextInt() + multipartFile.getOriginalFilename();
            } while (imageRepository.findByName(name).isPresent());
            multipartFile.transferTo(new File(path + name));
            imageRepository.save(Image.builder().name(name).path(path).build());
            return name;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<File> load(String filename) {
        if(imageRepository.findByName(filename).isPresent()) {
            return Optional.of(new File(path + filename));
        }
        return Optional.empty();
    }

    @Override
    public List<String> getAllImages() {
        return imageRepository.findAll().stream().map(Image::getName).collect(Collectors.toList());
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public File get404() {
        return new File(path + "404.png");
    }
}
