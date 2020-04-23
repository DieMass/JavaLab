package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import project.handlers.AuthHandshakeHandler;
import project.handlers.WebSocketMessagesHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Autowired
	private WebSocketMessagesHandler handler;

	@Autowired
	private AuthHandshakeHandler handshakeHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(handler, "/chat")
//				.setHandshakeHandler(handshakeHandler)
				.withSockJS();
	}
}

