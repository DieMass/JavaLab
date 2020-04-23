package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.models.SignInDto;
import project.models.TokenDto;
import project.services.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/signin")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @GetMapping
    public String getSignInPage(Authentication authentication) {
        return authentication == null ? "signin" : "redirect:/rooms";
    }

    @PostMapping
    public String post(HttpServletResponse response,
                       @RequestParam String email, @RequestParam String password) {
        System.out.println("SIC.p() " + email + " " + password);
         TokenDto tokenDto = signInService.signIn(SignInDto.builder().email(email).password(password).build());
        System.out.println("tD " + tokenDto);
         if(tokenDto != null) {
             Cookie cookie = new Cookie("token", tokenDto.getToken());
             cookie.setMaxAge(1200);
             response.addCookie(cookie);
             return "redirect:/rooms";
         }
         return "signin?error=invalid%login%or%password";
    }
}
