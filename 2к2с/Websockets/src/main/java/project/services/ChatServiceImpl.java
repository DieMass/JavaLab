package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.Chat;
import project.repositories.ChatRepository;

import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public List<Chat> getChatsById(Long userId) {
		return chatRepository.getChatsById(userId);
	}

	@Override
	public List<Chat> getAllChats() {
		return chatRepository.getAllChats();
	}

	@Override
	public Chat getById(Long chatId) {
		return chatRepository.find(chatId);
	}
}
