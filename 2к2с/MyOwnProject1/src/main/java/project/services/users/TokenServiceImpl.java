package project.services.users;

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
    public String getToken(String name, String email, String password) {
        if (name == null || email == null || password == null) return null;
        Map<String, Object> tokenData = new HashMap<>();
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
