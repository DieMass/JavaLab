package project.services;

import project.models.User;

public interface TokenService {

    String getToken(String name, String email, String password);

    String getData(String token, String field);
}
