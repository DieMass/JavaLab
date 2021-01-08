package die.mass.rabbitmqsimple;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@AllArgsConstructor
public class PassportConsoleReader {

	private final AmqpTemplate template;
	private static final Scanner scanner = new Scanner(System.in);
	private final Gson gson;
	private final FanoutExchange fanoutExchange;

	public void read() {
		long number = 0;
		int age = 0;
		String date;
		String name;
		String surname;
		var isCOrrect = false;
		System.out.println("Write name");
		name = scanner.nextLine();
		System.out.println("Write surname");
		surname = scanner.nextLine();
		System.out.println("Write passport number");
		do {
			String a = scanner.nextLine();
			try {
				number = Long.parseLong(a);
				isCOrrect = true;
			} catch (NumberFormatException e) {
				System.err.println("Incorrect number. Try again");
			}
		} while (!isCOrrect);
		isCOrrect = false;
		System.out.println("Write age");
		do {
			String a = scanner.nextLine();
			try {
				age = Integer.parseInt(a);
				isCOrrect = true;
			} catch (NumberFormatException e) {
				System.err.println("Incorrect age. Try again");
			}
		} while (!isCOrrect);
		System.out.println("Write registration date");
		date = scanner.nextLine();
		PassportDto dto = PassportDto.builder()
				.name(name)
				.surname(surname)
				.number(number)
				.age(age)
				.date(date)
				.build();
		template.convertAndSend(fanoutExchange.getName(), "", gson.toJson(dto));
		read();
	}
}