package die.mass.javalabmessagequeue.config;

import die.mass.javalabmessagequeue.handlers.AuthHandshakeHandler;
import die.mass.javalabmessagequeue.handlers.WebSocketMessagesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private WebSocketMessagesHandler handler;
//	@Autowired
//	private AuthHandshakeHandler handshakeHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(handler, "/chat");
//				.setHandshakeHandler(handshakeHandler)
//				.withSockJS();
	}
}