package die.mass.jlmq;

import com.google.gson.Gson;
import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import die.mass.jlmq.util.BeanUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;

@ClientEndpoint
@Getter
public class JlmqConsumer {

	private int port;
	private String uri;
	private String queueName;
	private Session session;
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

	@SneakyThrows
	JlmqConsumer(int port) {
		this.port = port;
		uri = "ws://localhost:" + port + "/chat";
		WebSocketContainer container = ContainerProvider.
				getWebSocketContainer();
		container.connectToServer(this, new URI(uri));
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("JC.oM() get message " + message);
		consumer.accept(gson.fromJson(message, Message.class));
	}

	public void send(Message message) {
		try {
			System.out.println("JC.send() " + gson.toJson(message));
			session.getBasicRemote().sendText(gson.toJson(message));
		} catch (IOException ex) {
			throw new IllegalArgumentException();
		}
	}

	private void subscribe() {
		send(Message.builder()
				.command(Command.subscribe)
				.queueName(queueName)
				.build());
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
			JlmqConsumer.this.subscribe();
			return JlmqConsumer.this;
		}
	}
}
