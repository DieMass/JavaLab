package die.mass.jlmq;

import die.mass.jlmq.protocol.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Queue;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MessageQueue {

	private String name;
	//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Queue<Message> queue;

	public boolean setTask(Message message) {
		return queue.add(message);
	}
}

