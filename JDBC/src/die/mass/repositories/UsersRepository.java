package die.mass.repositories;

import die.mass.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByFirstName(String firstName);
}
