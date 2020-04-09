package project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationProvider authenticationProvider;

		@Autowired
		@Qualifier(value = "jwtAuthenticationFilter")
		private GenericFilterBean jwtAuthenticationFilter;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.formLogin().disable();
			http.logout().disable();
			http.antMatcher("/api/**");
			http.httpBasic().disable();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.addFilterAt(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
		}

		@Autowired
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider);
		}
	}

	@Configuration
	@Order(2)
	public class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {

		@Autowired
		@Qualifier(value = "customUserDetailsService")
		private UserDetailsService userDetailsService;

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Autowired
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}

		@Autowired
		public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests();
			http.antMatcher("/**");
			http.formLogin()
					.loginPage("/signin")
					.usernameParameter("email")
					.defaultSuccessUrl("/user")
					.failureUrl("/signin?error=Incorrect email or password")
					.permitAll();
		}
	}
}
