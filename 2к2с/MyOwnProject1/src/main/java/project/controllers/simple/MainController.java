package project.controllers.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping
	public String get(HttpServletResponse response) {
		response.addCookie(new Cookie("localeInfo", "en"));
		return "redirect:/signin";
	}
}
