package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.services.ChatService;
import project.services.TokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

@Controller
public class RoomsController {

	@Autowired
	private ChatService chatService;
	@Autowired
	private TokenService tokenService;

	@GetMapping("/rooms")
	public ModelAndView get(HttpServletRequest request) {
		ModelAndView m = new ModelAndView("rooms");
		String token = getToken(request);
		if(token == null) return new ModelAndView("redirect:/signin");
		Long userId = Long.parseLong(tokenService.getData(token, "id"));
		m.addObject("chats", chatService.getAllChats());
		return m;
	}

	@GetMapping("/rooms/{chatId}")
	public ModelAndView getChat(HttpServletRequest request, @PathVariable("chatId") Long chatId) {
		ModelAndView m = new ModelAndView("chat");
		String token = getToken(request);
		if(token == null) return new ModelAndView("redirect:/signin");
		m.addObject("chat", chatService.getById(chatId));
		m.addObject("userName", tokenService.getData(token, "name"));
		return m;
	}

	@GetMapping("/info")
	public ResponseEntity<String> getInfo() {
		System.out.println("RC.gI()");
		return ResponseEntity.ok("{\"entropy\":293909549,\"origins\":[\"*:*\"],\"cookie_needed\":true,\"websocket\":true}");
	}

	private String getToken(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, "token");
		return cookie != null ? cookie.getValue() : null;
	}
}
