package project.services;

import com.sun.istack.NotNull;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenServiceImpl implements TokenService {

    final String key = "diemass1908";

    @Override
    public String getToken(@NotNull Long id, @NotNull String name, @NotNull String email, @NotNull String password) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("id", id.toString());
        tokenData.put("name", name);
        tokenData.put("email", email);
        tokenData.put("password", password);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        String token = jwtBuilder.signWith(SignatureAlgorithm.HS256, key).compact();
        return token;
    }

    @Override
    public String getData(String token, String field) {
        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        return (String)claims.get(field);
    }
}
