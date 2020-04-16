package project.controllers.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.dto.UserVerifiedDto;
import project.models.user.User;
import project.services.users.email.EmailService;
import project.services.users.TokenService;
import project.services.users.UserService;

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
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return authentication == null ? "signup" : "redirect:/user";
    }

    @PostMapping
    public ModelAndView doPost(@RequestParam("email") String email, @RequestParam("password") String passwordEnc,
                               @RequestParam("name") String name) {
        if(!userService.userExists(name)) {
            String password = encoder.encode(passwordEnc);
            String token = tokenService.getToken(name, email, password);
            userService.saveUser(User.builder()
                    .name(name).email(email).password(password)
                    .token(token).build());
            emailService.sendMessage(email, UserVerifiedDto.builder().name(name).token(token).build());
            return new ModelAndView("redirect:/signin");
        }
        ModelAndView mv = new ModelAndView("/signup");
        mv.addObject("error", "user " + name + " already exists");
        return mv;
    }
}
