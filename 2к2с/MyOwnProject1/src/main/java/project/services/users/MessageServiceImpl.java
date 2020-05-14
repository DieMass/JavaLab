package project.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.user.MessageAdminListDto;
import project.dto.user.MessageDto;
import project.models.user.Message;
import project.repositories.users.MessageRepository;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public List<Message> getAllMessages(Long userId) {
		return messageRepository.getMessagesByUser(userId);
	}

	@Override
	public void save(MessageDto messageDto) {
		messageRepository.save(MessageDto.toMessage(messageDto));
	}

	@Override
	public List<MessageAdminListDto> getAllUsers() {
		return messageRepository.getAllUsers();
	}
}
