package project.security.jwt.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import project.security.details.UserDetailsImpl;

import java.util.Collection;

// объект, который работает с JWT-аутентификацией
public class JwtAuthentication implements Authentication {
	// флаг аутентификации
	private boolean isAuthenticated = false;
	// токен
	private String token;
	// информация о пользователе
	private UserDetailsImpl userDetails;

	public JwtAuthentication(String token) {
		this.token = token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDetails.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return userDetails;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	@Override
	public String getName() {
		return this.token;
	}

	public void setUserDetails(UserDetailsImpl userDetails) {
		this.userDetails = userDetails;
	}
}

