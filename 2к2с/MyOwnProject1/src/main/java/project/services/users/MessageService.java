package project.services.users;

import project.dto.user.MessageAdminListDto;
import project.dto.user.MessageDto;
import project.models.user.Message;

import java.util.List;

public interface MessageService {

	List<Message> getAllMessages(Long userId);
	void save(MessageDto messageDto);

	List<MessageAdminListDto> getAllUsers();
}
