package project.services;

import project.models.Chat;

import java.util.List;

public interface ChatService {

	List<Chat> getChatsById(Long userId);

	List<Chat> getAllChats();

	Chat getById(Long chatId);
}
