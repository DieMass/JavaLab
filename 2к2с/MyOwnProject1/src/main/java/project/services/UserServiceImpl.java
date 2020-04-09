package project.services;

import project.models.user.User;
import project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void verifyUser(String name) {
        userRepository.verifyUser(name);
    }

    @Override
    public boolean userExists(String name) {
        return userRepository.findByName(name).isPresent();
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
