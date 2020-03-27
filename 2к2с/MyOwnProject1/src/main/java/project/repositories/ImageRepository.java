package project.repositories;

import project.models.Image;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {

    Optional<Image> findByName(String name);
}
