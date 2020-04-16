package project.controllers.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.MessageAdminListDto;
import project.dto.MessageDto;
import project.services.users.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ChatService chatService;

	// получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
	@PostMapping
	public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message) {
		chatService.throwMessage(message);
		return ResponseEntity.ok().build();
	}

	// получить все сообщения для текущего запроса
	@SneakyThrows
	@GetMapping("/user")
	public ResponseEntity<List<MessageDto>> getMessagesForUser(@RequestParam("userId") Long userId) {
		return ResponseEntity.ok(chatService.getMessageForUser(userId));
	}

	@SneakyThrows
	@GetMapping("/admin")
	public ResponseEntity<List<MessageDto>> getMessagesForAdmin(@RequestParam("userId") Long userId) {
		return ResponseEntity.ok(chatService.getMessageForAdmin(userId));
	}

	@GetMapping("/start")
	public ResponseEntity<List<MessageDto>> start(@RequestParam("userId") Long userId) {
		return ResponseEntity.ok(chatService.start(userId));
	}

	@GetMapping("/chatlist")
	public ResponseEntity<List<MessageAdminListDto>> getMessagesForAdmin() {
		List<MessageAdminListDto> response = messageService.getAllUsers();
		return ResponseEntity.ok(response);
	}
}
