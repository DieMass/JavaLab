package die.mass.jlmq;

import com.google.gson.Gson;
import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import die.mass.jlmq.util.BeanUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ClientEndpoint
@Getter
public class JlmqConsumer {

	private int port;
	private String uri;
	private String queueName;
	private Consumer<Message> consumer = message -> {
		send(Message.fromMessageWithCommand(message, Command.accepted));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new IllegalArgumentException();
		}
		System.out.println(message.getBody());
		send(Message.fromMessageWithCommand(message, Command.completed));
	};
	private Gson gson;
	private ConsumerStompSessionHandler consumerStompSessionHandler;

	@SneakyThrows
	JlmqConsumer(int port) {
		this.port = port;
		uri = "ws://localhost:" + port + "/chat";
	}

	public void send(Message message) {
		consumerStompSessionHandler.sendMessage(message);
	}


	static Builder builder(int port) {
		return new JlmqConsumer(port).new Builder();
	}

	public class Builder {

		public Builder subscribe(String queueName) {
			JlmqConsumer.this.queueName = queueName;
			return this;
		}

		public Builder onReceive(Consumer<Message> consumer) {
			JlmqConsumer.this.consumer = consumer;
			return this;
		}

		@SneakyThrows
		public JlmqConsumer create() {
			gson = BeanUtil.getBean(Gson.class);
			consumerStompSessionHandler = new ConsumerStompSessionHandler(consumer, queueName);
			List<Transport> transports = new ArrayList<>(1);
			transports.add(new WebSocketTransport(new StandardWebSocketClient()));
			WebSocketClient webSocketClient = new SockJsClient(transports);
			WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
			stompClient.setMessageConverter(new StringMessageConverter());
			stompClient.connect(uri, consumerStompSessionHandler);
			return JlmqConsumer.this;
		}
	}
}
