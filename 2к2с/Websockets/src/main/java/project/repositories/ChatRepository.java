package project.repositories;

import project.models.Chat;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {

	List<Chat> getChatsById(Long userId);

	List<Chat> getAllChats();
}
