package project.services.users;

import project.dto.MessageAdminListDto;
import project.dto.MessageDto;
import project.models.user.Message;

import java.util.List;

public interface MessageService {

	List<Message> getAllMessages(Long userId);
	void save(MessageDto messageDto);

	List<MessageAdminListDto> getAllUsers();
}
