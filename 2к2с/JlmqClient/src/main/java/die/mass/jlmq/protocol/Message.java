package die.mass.jlmq.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

	private Long id;
	@Enumerated(value = EnumType.STRING)
	private Command command;
	private String queueName;
	private Object body;

	public static Message fromMessageWithCommand(Message message, Command command) {
		return Message.builder().id(message.getId())
				.queueName(message.queueName)
				.body(message.body)
				.command(command)
				.build();
	}

//	boolean isAccepted;
//	boolean isCompleted;
//	public Message accepted() {
//		isAccepted = true;
//		return this;
//	}
//	public Message completed() {
//		isCompleted = true;
//		return this;
//	}
}
