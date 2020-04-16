package project.services.users;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.SignInDto;
import project.dto.TokenDto;
import project.models.user.User;
import project.repositories.users.UserRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private String secret = "qwerty007";

	@Override
	public TokenDto signIn(SignInDto signInData) {
		// получаем пользователя по его email
		Optional<User> userOptional = userRepository.findByEmail(signInData.getEmail());
		// если у меня есть этот пользвователь
		if (userOptional.isPresent()) {
			// получаем его
			User user = userOptional.get();
			// если пароль подходит
			if (passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
				// создаем токен
				String token = Jwts.builder()
						.setSubject(user.getId().toString()) // id пользователя
						.claim("name", user.getName()) // имя
						.claim("role", user.getRole().name()) // роль
						.signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
						.compact(); // преобразовали в строку
				return new TokenDto(token);
			} else throw new AccessDeniedException("Wrong email/password");
		} else throw new AccessDeniedException("User not found");
	}
}
