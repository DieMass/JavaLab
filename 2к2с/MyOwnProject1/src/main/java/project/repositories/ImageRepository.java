package project.repositories;

import project.models.user.Image;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {

    Optional<Image> findByName(String name);
}
