package project.services;

import project.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    boolean saveUser(User user);

    void verifyUser(String name);

    boolean userExists(String name);

    Optional<User> getUserByName(String name);
}
