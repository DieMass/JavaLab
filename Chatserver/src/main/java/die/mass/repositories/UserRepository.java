package die.mass.repositories;

import die.mass.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    String contains(String name);
    ConnectionWrap getConnectionWrap();

}
