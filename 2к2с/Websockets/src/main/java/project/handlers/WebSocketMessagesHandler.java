package project.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project.models.ChatMessage;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

	private static final Map<String, Map<String, WebSocketSession>> sessions = new HashMap<>();

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String messageText = (String) message.getPayload();
		ChatMessage chatMessageFromJson = objectMapper.readValue(messageText, ChatMessage.class);
		if (!sessions.containsKey(chatMessageFromJson.getChat())) {
			sessions.put(chatMessageFromJson.getChat(), new HashMap<>());
		}
		Map<String, WebSocketSession> map = sessions.get(chatMessageFromJson.getChat());
		if (!map.containsKey(chatMessageFromJson.getSender())) {
			map.put(chatMessageFromJson.getSender(), session);
		}
		for (WebSocketSession currentSession : map.values()) {
			currentSession.sendMessage(message);
		}
	}
}

