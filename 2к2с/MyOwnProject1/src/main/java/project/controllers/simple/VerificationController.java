package project.controllers.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import project.services.users.TokenService;
import project.services.users.UserService;

@Controller
@RequestMapping(value = "/verification")
public class VerificationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(@RequestParam("token") String token) {
        String name = tokenService.getData(token, "name");
        userService.verifyUser(name);
        ModelAndView mv = new ModelAndView("redirect:/user");
        mv.addObject("name", name);
        return mv;
    }
}
