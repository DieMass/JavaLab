package die.mass.javalabmessagequeue.server;

import com.google.gson.Gson;
import die.mass.javalabmessagequeue.util.BeanUtil;
import lombok.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Queue;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = {"gson", "simpMessagingTemplate"})
public class ConsumerThread extends Thread {

	private Queue<Message> queue;
	private final Gson gson = BeanUtil.getBean(Gson.class);
	private final SimpMessagingTemplate simpMessagingTemplate = BeanUtil.getBean(SimpMessagingTemplate.class);
	private String queueName;
	private boolean consumerIsConnected;

	@SneakyThrows
	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				if (queue.isEmpty() || !consumerIsConnected) {
					queue.wait();
				}
				if (!queue.isEmpty() && consumerIsConnected) {
					String s = gson.toJson(queue.poll());
					System.out.println("CT.r() message is: " + s);
					System.out.println("CT.R() queueName: " + queueName);
					simpMessagingTemplate.convertAndSend("/queue/" + queueName, s);
					queue.wait();
				}
			}
		}
	}

	public void setTask(Message message) {
		queue.add(message);
		synchronized (queue) {
			queue.notify();
		}
	}
}
