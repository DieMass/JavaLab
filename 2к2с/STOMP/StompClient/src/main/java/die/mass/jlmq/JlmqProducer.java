package die.mass.jlmq;


import com.google.gson.Gson;
import die.mass.jlmq.util.BeanUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
@Getter
public class JlmqProducer {

	private int port;
	private String uri;
	private String queueName;
	private Gson gson;
	private ProducerStompSessionHandler producerStompSessionHandler;

	@SneakyThrows
	JlmqProducer(int port) {
		this.port = port;
		uri = "ws://localhost:" + port + "/chat";
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient webSocketClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setMessageConverter(new StringMessageConverter());
		producerStompSessionHandler = new ProducerStompSessionHandler();
		stompClient.connect(uri, producerStompSessionHandler);
	}

	public void sendMessage(String message) {
		producerStompSessionHandler.sendMessage(message, queueName);
	}

	static JlmqProducer.Builder builder(int port) {
		return new JlmqProducer(port).new Builder();
	}

	public class Builder {

		public JlmqProducer.Builder toQueue(String queueName) {
			JlmqProducer.this.queueName = queueName;
			return this;
		}

		@SneakyThrows
		public JlmqProducer create() {
			gson = BeanUtil.getBean(Gson.class);
			return JlmqProducer.this;
		}
	}

}
