package project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.UserDto;
import project.security.details.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class ProfileRestController {

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/self")
	public ResponseEntity<UserDto> getSelf() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
		System.out.println(userDetails);
		return ResponseEntity.ok(UserDto.builder()
				.name(userDetails.getUsername())
				.id(userDetails.getUserId())
				.build());
	}
}
