package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.UserDto;
import project.services.users.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsersRestController {

	@Autowired
	private UserService usersService;


	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getUsers() {
		return ResponseEntity.ok(usersService.getAllUsers()
				.stream().map(UserDto::from).collect(Collectors.toList()));
	}

}
