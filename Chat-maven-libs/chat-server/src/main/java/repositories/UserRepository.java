package die.mass.repositories;

import die.mass.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean contains(String name, Long password);

}
