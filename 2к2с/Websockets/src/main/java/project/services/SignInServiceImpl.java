package project.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.models.SignInDto;
import project.models.TokenDto;
import project.models.User;
import project.repositories.UserRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenService tokenService;

	@Override
	public TokenDto signIn(SignInDto signInData) {
		// получаем пользователя по его email
		Optional<User> userOptional = userRepository.findByEmail(signInData.getEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			// если пароль подходит
			if (passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
				// создаем токен
				return TokenDto.builder().token(
						tokenService.getToken(user.getId(),user.getName(), user.getEmail(),user.getPassword())).build();
			} else throw new AccessDeniedException("Wrong email/password");
		} else throw new AccessDeniedException("User not found");
	}
}
