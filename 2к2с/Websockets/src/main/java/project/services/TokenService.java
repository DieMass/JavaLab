package project.services;

public interface TokenService {

    String getToken(Long id, String name, String email, String password);

    String getData(String token, String field);
}
