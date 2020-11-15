package die.mass.rabbitmq.consumers;

import com.google.gson.Gson;
import die.mass.rabbitmq.dto.PassportDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class ConfirmListener implements Consumer {

	private final Gson gson;
	private final AmqpTemplate template;
	private final Scanner scanner;
	private final TopicExchange topic;

	public ConfirmListener(Gson gson, AmqpTemplate template, @Qualifier("consoleScanner") Scanner scanner, TopicExchange topic) {
		this.gson = gson;
		this.template = template;
		this.scanner = scanner;
		this.topic = topic;
	}

	@RabbitListener(queues = "confirmationTopicQueue")
	@Override
	public void listen(Message message) {
		var dto = gson.fromJson(new String(message.getBody()), PassportDto.class);
//		System.out.printf("If user %s %s %s are corect, print \"+\", else \"-\"\n", dto.getSurname(), dto.getName(), dto.getNumber());
//		String inputLine;
//		while (!(inputLine = scanner.nextLine()).equals("+") && !inputLine.equals("-")) {
//			System.err.println("Incorrect answer. Please try again");
//		}
//		if(inputLine.equals("+")) {
		dto.setIsConfirmed(true);
		template.convertAndSend(topic.getName(), String.format("%s.confirmed.%s", "passport", dto.getBirthCountry()), gson.toJson(dto));
//		}
	}
}
