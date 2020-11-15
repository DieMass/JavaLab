package die.mass.rabbitmq.producers;

import com.google.gson.Gson;
import die.mass.rabbitmq.dto.PassportDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PassportFileReader extends AbstractPassportReader {

	public PassportFileReader(@Qualifier("fileScanner") Scanner scanner, AmqpTemplate template,
								 Gson gson, FanoutExchange fanoutExchange) {
		this.scanner = scanner;
		this.template = template;
		this.gson = gson;
		this.fanoutExchange = fanoutExchange;
	}

	@Override
	public void read() {
		if(scanner.hasNext()) {
			PassportDto dto = PassportDto.builder()
					.surname(scanner.nextLine())
					.name(scanner.nextLine())
					.patronymic(scanner.nextLine())
					.series(Integer.parseInt(scanner.nextLine()))
					.number(Integer.parseInt(scanner.nextLine()))
					.birthYear(Integer.parseInt(scanner.nextLine()))
					.date(scanner.nextLine())
					.birthCountry(scanner.nextLine())
					.service(scanner.nextLine())
					.build();
			template.convertAndSend(fanoutExchange.getName(), String.format("%s.%s.%s", dto.getService(), "not_confirmed", dto.getBirthCountry()), gson.toJson(dto));
		}
	}
}
