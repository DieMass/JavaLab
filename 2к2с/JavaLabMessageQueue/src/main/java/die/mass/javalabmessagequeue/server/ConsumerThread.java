package die.mass.javalabmessagequeue.server;

import com.google.gson.Gson;
import die.mass.javalabmessagequeue.util.BeanUtil;
import lombok.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Queue;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = {"gson"})
public class ConsumerThread extends Thread {

	private Queue<Message> queue;
	private WebSocketSession session;
	private final Gson gson = BeanUtil.getBean(Gson.class);

	@SneakyThrows
	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				if (queue.isEmpty() || session == null) {
					queue.wait();
				}
				if (!queue.isEmpty() && session != null) {
					String s = gson.toJson(queue.poll());
					System.out.println("CT.r() message is: " + s);
					session.sendMessage(new TextMessage(s));
					queue.wait();
				}
			}
		}
	}

	public void setTask(Message message) {
		queue.add(message);
		System.out.println("CT.sT() queue is: " + queue);
		synchronized (queue) {
			queue.notify();
		}
	}
}
