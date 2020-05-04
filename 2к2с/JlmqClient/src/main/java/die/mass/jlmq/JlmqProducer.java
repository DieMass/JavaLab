package die.mass.jlmq;


import com.google.gson.Gson;
import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import die.mass.jlmq.util.BeanUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
@Getter
public class JlmqProducer {

	private int port;
	private String uri;
	private Session session;
	private String queueName;
	private Gson gson;

	@SneakyThrows
	JlmqProducer(int port) {
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

	@SneakyThrows
	public void sendMessage(String message) {
		System.out.println("JP.send() " + message);
		session.getBasicRemote().sendText(gson.toJson(Message.builder()
				.command(Command.send)
				.body(message)
				.queueName(queueName)
				.build()));
	}

	@OnMessage
	public void getMessage(String string) {
		System.out.println(string);
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
