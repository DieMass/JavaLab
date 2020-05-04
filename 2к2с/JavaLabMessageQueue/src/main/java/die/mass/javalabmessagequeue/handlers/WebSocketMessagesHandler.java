package die.mass.javalabmessagequeue.handlers;

import com.google.gson.Gson;
import die.mass.javalabmessagequeue.Command;
import die.mass.javalabmessagequeue.server.ConsumerThread;
import die.mass.javalabmessagequeue.server.Message;
import die.mass.javalabmessagequeue.server.Task;
import die.mass.javalabmessagequeue.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayDeque;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

	@Autowired
	private Gson gson;
	@Autowired
	@Qualifier("consumerThreads")
	private Map<String, ConsumerThread> consumerThreads;
	@Autowired
	private TaskService taskService;


	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String messageText = (String) message.getPayload();
		Message mes = gson.fromJson(messageText, Message.class);
		System.out.println("WSMH.hM() " + messageText);
		switch (mes.getCommand()) {
			case send:
				Task task = Task.fromMessage(mes, gson.toJson(mes.getBody()));
				taskService.save(task);
				createConsumerThreadIfNotExist(mes.getQueueName(), null);
				mes = Message.from(mes).command(Command.receive).id(task.getId()).build();
				consumerThreads.get(mes.getQueueName()).setTask(mes);
				notifyQueue(mes.getQueueName());
				System.out.println("message " + mes.getBody() + " set to queue " + mes.getQueueName());
				break;
			case subscribe:
				createConsumerThreadIfNotExist(mes.getQueueName(), session);
				consumerThreads.get(mes.getQueueName()).setSession(session);
				notifyQueue(mes.getQueueName());
				break;
			case accepted:
			case completed:
				taskService.update(Task.fromMessage(mes, gson.toJson(mes.getBody())));
				if (mes.getCommand().equals(Command.completed)) notifyQueue(mes.getQueueName());
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	private void createConsumerThreadIfNotExist(String queueName, WebSocketSession session) {
		if (!consumerThreads.containsKey(queueName)) {
			consumerThreads.put(queueName,
					ConsumerThread.builder().queue(new ArrayDeque<>()).session(session).build());
			consumerThreads.get(queueName).start();
		}
	}

	private void notifyQueue(String queueName) {
		synchronized (consumerThreads.get(queueName).getQueue()) {
			consumerThreads.get(queueName).getQueue().notify();
		}
	}
}

