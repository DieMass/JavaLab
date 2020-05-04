package die.mass.jlmq;

import com.google.gson.Gson;
import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import die.mass.jlmq.util.BeanUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

public class ConsumerStompSessionHandler extends StompSessionHandlerAdapter {

	private Gson gson;
	private StompSession session;
	private Consumer<Message> consumer;
	private String queueName;

	public ConsumerStompSessionHandler(Consumer<Message> consumer, String queueName) {
		this.consumer = consumer;
		this.queueName = queueName;
		gson = BeanUtil.getBean(Gson.class);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/queue/" + queueName, this);
		this.session = session;
		session.send("/app/subscribe", queueName);
		System.out.println("subscribe");
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		System.out.println("CSSH.hF()");
		Message message = gson.fromJson((String) payload, Message.class);
		System.out.println("JC.oM() get message " + message);
		consumer.accept(message);
	}

	@SneakyThrows
	public void sendMessage(Message message) {
			System.out.println("JC.send() " + message);
			session.send("/app/" + message.getCommand().name(), gson.toJson(message));
	}
}
