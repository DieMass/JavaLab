package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.SignInDto;
import project.dto.TokenDto;
import project.services.SignInService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/signin")
public class SignInRestController {

	@Autowired
	private SignInService signInService;

	@PostMapping
	public ResponseEntity<TokenDto> signIn(HttpServletRequest request,
										   HttpServletResponse response,
										   @RequestBody SignInDto signInData) throws IOException {
		return ResponseEntity.ok(signInService.signIn(signInData));
	}
}
