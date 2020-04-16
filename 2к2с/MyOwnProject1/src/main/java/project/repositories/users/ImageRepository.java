package project.repositories.users;

import project.models.user.Image;
import project.repositories.general.CrudRepository;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {

    Optional<Image> findByName(String name);
}
