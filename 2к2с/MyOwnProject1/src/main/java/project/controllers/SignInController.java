package project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/signin")
@PreAuthorize("permitAll()")
public class SignInController {

    @GetMapping
    public String getSignInPage(Authentication authentication) {
        return authentication == null ? "signin" : "redirect:/user";
    }

}
