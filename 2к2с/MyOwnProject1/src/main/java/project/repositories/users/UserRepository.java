package project.repositories.users;

import project.models.user.User;
import project.repositories.general.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    void verifyUser(String name);
}
