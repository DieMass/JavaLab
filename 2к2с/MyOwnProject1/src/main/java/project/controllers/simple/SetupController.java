package project.controllers.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.models.user.User;
import project.security.details.UserDetailsImpl;
import project.services.devices.SetupService;

@Controller
@RequestMapping("/setups")
public class SetupController {

	@Autowired
	private SetupService setupService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ModelAndView doGet(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetails.getUser();
		ModelAndView m = new ModelAndView("setups");
		m.addObject("user", user);
		m.addObject("setups", setupService.getAllSetupsForUser(user.getId()));
		return m;
	}

}


