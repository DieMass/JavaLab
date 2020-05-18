package project.services.users;

import project.models.user.User;

public interface TokenService {

    String getToken(String name, String email, String password);
    String getData(String token, String field);
    String getЕбучийToken(User user);
}
