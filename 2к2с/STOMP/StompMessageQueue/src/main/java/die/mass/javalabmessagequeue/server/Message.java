package die.mass.javalabmessagequeue.server;

import die.mass.javalabmessagequeue.Command;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

	private Long id;
	@Enumerated(value = EnumType.STRING)
	private Command command;
	private String  queueName;
	private Object body;

	public static Message.MessageBuilder from(Message message) {
		return Message.builder()
				.id(message.id)
				.body(message.body)
				.command(message.command)
				.queueName(message.queueName);
	}
}
