package die.mass.jlmq;

import com.google.gson.Gson;
import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import die.mass.jlmq.util.BeanUtil;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class ProducerStompSessionHandler extends StompSessionHandlerAdapter {

	private Gson gson;
	private StompSession session;

	public ProducerStompSessionHandler() {
		gson = BeanUtil.getBean(Gson.class);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/queue", this);
		session.send("/app/", "hi man");
		this.session = session;
	}

	@SneakyThrows
	public void sendMessage(String message, String queueName) {
		System.out.println("JP.send() " + message);
		session.send("/app/task",
				gson.
						toJson(
								Message
										.builder()
				.command(Command.send)
				.body(message)
				.queueName(queueName)
				.build()));
	}
}
