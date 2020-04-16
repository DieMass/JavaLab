package project.dto;

import lombok.*;
import project.models.user.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDto {
	private String sender;
	private String receiver;
	private String text;
	private LocalDateTime localDateTime;

	public static List<MessageDto> from(List<Message> messages) {
		return messages.stream().map(MessageDto::from).collect(Collectors.toList());
	}

	public static MessageDto from(Message message) {
		return MessageDto.builder()
				.sender(String.valueOf(message.getSender()))
				.receiver(String.valueOf(message.getReceiver()))
				.text(message.getText())
				.localDateTime(message.getDateTime())
				.build();
	}

	public static Message toMessage(MessageDto messageDto) {
		return Message.builder()
				.sender(Long.valueOf(messageDto.getSender()))
				.receiver(Long.valueOf(messageDto.getReceiver()))
				.text(messageDto.getText())
				.dateTime(LocalDateTime.now())
				.build();
	}
}
