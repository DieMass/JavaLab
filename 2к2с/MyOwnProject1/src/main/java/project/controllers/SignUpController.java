package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import project.dto.UserVerifiedDto;
import project.models.User;
import project.services.EmailService;
import project.services.TokenService;
import project.services.UserService;

@Controller
@RequestMapping(value = "/signup")
@PreAuthorize("permitAll()")
public class SignUpController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenService tokenService;


    @GetMapping
    public String  doGet(Authentication authentication) {
        return authentication == null ? "signup" : "redirect:/user";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam("email") String email, @RequestParam("password") String passwordEnc,
                               @RequestParam("name") String name) {
        if(!userService.userExists(name)) {
            String password = encoder.encode(passwordEnc);
            String token = tokenService.getToken(name, email, password);
            userService.saveUser(User.builder()
                    .name(name).email(email).password(password)
                    .token(token).build());
            emailService.sendMessage(email, UserVerifiedDto.builder().name(name).token(token).build());
            return new ModelAndView("/signin");
        }
        ModelAndView mv = new ModelAndView("/signup");
        mv.addObject("error", "user " + name + " already exists");
        return mv;
    }
}
