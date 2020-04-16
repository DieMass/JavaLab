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
import project.services.users.UserService;

@Controller
@RequestMapping(value = "/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ModelAndView doGet(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetails.getUser();
		ModelAndView mv = new ModelAndView("/user");
		mv.addObject("user", user);
		return mv;
	}

//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView doGet(@RequestParam("name") String name) {
//        Optional<User> userCandidate = userService.getUserByName(name);
//        if(userCandidate.isPresent()) {
//            ModelAndView mv = new ModelAndView("user");
//            mv.addObject("user", userCandidate.get());
//            return mv;
//        }
//        return new ModelAndView("redirect:/404");
//    }

}
