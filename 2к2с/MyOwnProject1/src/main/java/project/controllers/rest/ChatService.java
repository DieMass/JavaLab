package project.controllers.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.user.MessageDto;
import project.models.user.Message;
import project.services.users.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatService {

	@Autowired
	private MessageService messageService;
	private final Map<Long, List<MessageDto>> userMapOfLists = new HashMap<>();
	private final Map<Long, List<MessageDto>> adminMapOfLists = new HashMap<>();

	public List<MessageDto> start(Long id) {
		if(!userMapOfLists.containsKey(1L)) userMapOfLists.put(1L, new ArrayList<>());
		if(!adminMapOfLists.containsKey(1L)) adminMapOfLists.put(1L, new ArrayList<>());
		if(!userMapOfLists.containsKey(id)) userMapOfLists.put(id, new ArrayList<>());
		if(!adminMapOfLists.containsKey(id)) adminMapOfLists.put(id, new ArrayList<>());
		return MessageDto.from(messageService.getAllMessages(id));
	}

	@SneakyThrows
	public List<MessageDto> getMessageForUser(Long id) {
		if (!userMapOfLists.containsKey(id)) {
			return start(id);
		}
		if (userMapOfLists.get(id).isEmpty()) {
			synchronized (userMapOfLists.get(id)) {
				userMapOfLists.get(id).wait();
			}
		}
		List<MessageDto> messages = new ArrayList<>(userMapOfLists.get(id));
		userMapOfLists.get(id).clear();
		return messages;
	}

	@SneakyThrows
	public List<MessageDto> getMessageForAdmin(Long id) {
		if (!adminMapOfLists.containsKey(id)) {
			return start(id);
		}
		if (adminMapOfLists.get(id).isEmpty()) {
			synchronized (adminMapOfLists.get(id)) {
				adminMapOfLists.get(id).wait();
			}
		}
		List<MessageDto> messages = new ArrayList<>(adminMapOfLists.get(id));
		adminMapOfLists.get(id).clear();
		return messages;
	}

	public void throwMessage(MessageDto messageDto) {
		Message message = MessageDto.toMessage(messageDto);
		userMapOfLists.get(message.getReceiver()).add(messageDto);
		adminMapOfLists.get(message.getReceiver()).add(messageDto);
		userMapOfLists.get(message.getSender()).add(messageDto);
		adminMapOfLists.get(message.getSender()).add(messageDto);
		messageService.save(messageDto);
		synchronized (userMapOfLists.get(message.getSender())) {
			userMapOfLists.get(message.getSender()).notify();
		}
		synchronized (userMapOfLists.get(message.getReceiver())) {
			userMapOfLists.get(message.getReceiver()).notify();
		}
		synchronized (adminMapOfLists.get(message.getSender())) {
			adminMapOfLists.get(message.getSender()).notify();
		}
		synchronized (adminMapOfLists.get(message.getReceiver())) {
			adminMapOfLists.get(message.getReceiver()).notify();
		}
	}
}
