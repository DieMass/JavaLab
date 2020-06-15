package project.security.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.models.user.State;
import project.models.user.User;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
@Builder
public class UserDetailsImpl implements UserDetails {

	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String authority = user.getRole().toString();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
		return Collections.singleton(simpleGrantedAuthority);
	}


	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return State.CONFIRMED.equals(user.getState());
	}

	public Long getUserId() {
		return user.getId();
	}
}
