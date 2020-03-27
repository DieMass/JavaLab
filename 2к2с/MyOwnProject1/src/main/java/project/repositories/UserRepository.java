package project.repositories;

import project.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    void verifyUser(String name);
}
