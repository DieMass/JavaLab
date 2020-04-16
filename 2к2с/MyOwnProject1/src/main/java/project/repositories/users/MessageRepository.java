package project.repositories.users;

import project.dto.MessageAdminListDto;
import project.models.user.Message;
import project.repositories.general.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

	List<Message> getMessagesByUser(Long userId);

	List<MessageAdminListDto>getAllUsers();
}
