package die.mass.javalabmessagequeue.controllers;

import com.google.gson.Gson;
import die.mass.javalabmessagequeue.Command;
import die.mass.javalabmessagequeue.server.ConsumerThread;
import die.mass.javalabmessagequeue.server.Message;
import die.mass.javalabmessagequeue.server.Task;
import die.mass.javalabmessagequeue.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayDeque;
import java.util.Map;

@Controller
public class MessageController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private Gson gson;
	@Autowired
	@Qualifier("consumerThreads")
	private Map<String, ConsumerThread> consumerThreads;

	@MessageMapping("/task")
	public void get(@Payload String message) {
		Message mes = gson.fromJson(message, Message.class);
		Task task = Task.fromMessage(mes, gson.toJson(mes.getBody()));
		taskService.save(task);
		createConsumerThreadIfNotExist(mes.getQueueName());
		mes = Message.from(mes).command(Command.receive).id(task.getId()).build();
		consumerThreads.get(mes.getQueueName()).setTask(mes);
		notifyQueue(mes.getQueueName());
		System.out.println("message " + mes.getBody() + " set to queue " + mes.getQueueName());
	}

	@MessageMapping("/subscribe")
	public void subscribe(@Payload String queueName) {
		createConsumerThreadIfNotExist(queueName);
		consumerThreads.get(queueName).setConsumerIsConnected(true);
		notifyQueue(queueName);
	}

	@MessageMapping("/accepted")
	public void accepted(@Payload String message) {
		updateMessage(message);
	}

	@MessageMapping("/completed")
	public void completed(@Payload String message) {
		updateMessage(message);
	}

	private void updateMessage(String message) {
		Message mes = gson.fromJson(message, Message.class);
		taskService.update(Task.fromMessage(mes, gson.toJson(mes.getBody())));
		if (mes.getCommand().equals(Command.completed)) {
			synchronized (consumerThreads.get(mes.getQueueName()).getQueue()) {
				consumerThreads.get(mes.getQueueName()).getQueue().notify();
			}
		}
	}

	private void createConsumerThreadIfNotExist(String queueName) {
		if (!consumerThreads.containsKey(queueName)) {
			consumerThreads.put(queueName,
					ConsumerThread.builder()
							.queue(new ArrayDeque<>())
							.queueName(queueName)
							.build());
			System.out.println("MC.cCTINE()");
			consumerThreads.get(queueName).start();
		}
	}

	private void notifyQueue(String queueName) {
		synchronized (consumerThreads.get(queueName).getQueue()) {
			consumerThreads.get(queueName).getQueue().notify();
		}
	}
}
