package die.mass.javalabmessagequeue.server;

import die.mass.javalabmessagequeue.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String queueName;
	@Column(length = 4096)
	private String body;
	private boolean isAccepted;
	private boolean isCompleted;

	public static Task fromMessage(Message message, String bodyToString) {
		return Task.builder()
				.id(message.getId())
				.queueName(message.getQueueName())
				.body(bodyToString)
				.isCompleted(message.getCommand().equals(Command.completed))
				.isAccepted(message.getCommand().equals(Command.accepted)
						|| message.getCommand().equals(Command.completed))
				.build();
	}

}
