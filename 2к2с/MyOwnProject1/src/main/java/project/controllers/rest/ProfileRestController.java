package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.dto.user.UserDto;
import project.repositories.devices.CompanyRepository;
import project.security.details.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class ProfileRestController {

	@Autowired
	private CompanyRepository companyRepository;

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

	@PreAuthorize("permitAll()")
	@GetMapping("/with7nm/company/{companyId}")
	public ResponseEntity<?> getCpus7nm(@PathVariable("companyId") Long companyId) {
		return ResponseEntity.ok(companyRepository.findById(companyId).get().getCpusWith7nm());
	}
}
