package project.controllers.simple;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.models.user.Role;
import project.models.user.User;
import project.security.details.UserDetailsImpl;

@Controller
public class ChatController {

	@GetMapping("/support")
	@PreAuthorize("isAuthenticated()")
	public String getChatPage(Authentication authentication, Model model) {
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			return "admin_support";
		}
		User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
		model.addAttribute("userId", user.getId());
		model.addAttribute("role", user.getRole().toString());
		return "chat";
	}

	@GetMapping("/chat")
	@PreAuthorize("isAuthenticated()")
	public String getAdminChat(Authentication authentication, @RequestParam(name = "userId", defaultValue = "1") Long userId, Model model) {
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			model.addAttribute("userId", userId);
			model.addAttribute("role", Role.ADMIN.toString());
			return "admin_chat";
		}
		return "redirect:/support";
	}
}
