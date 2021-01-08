package die.mass.rabbitmq.producers;

import com.google.gson.Gson;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import die.mass.rabbitmq.consumers.PdfCreator;
import die.mass.rabbitmq.dto.PassportDto;
import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

@Component
public class PassportConsoleReader extends AbstractPassportReader {

	public PassportConsoleReader(@Qualifier("consoleScanner") Scanner scanner, AmqpTemplate template,
								 Gson gson, FanoutExchange fanoutExchange) {
		this.scanner = scanner;
		this.template = template;
		this.gson = gson;
		this.fanoutExchange = fanoutExchange;
	}

	public void read() {
		int series;
		long number;
		int age;
		String date;
		String name;
		String surname;
		String patronymic;
		String country;
		var isCOrrect = false;
		System.out.println("Write name");
		name = scanner.nextLine();
		System.out.println("Write surname");
		surname = scanner.nextLine();
		System.out.println("Write patronymic");
		patronymic = scanner.nextLine();
		System.out.println("Write passport series");
		series = (int) get(Integer::parseInt, "series");
		System.out.println("Write passport number");
		number = (long) get(Long::parseLong, "number");
		System.out.println("Write birth year");
		age = (int) get(Integer::parseInt, "birth year");
		System.out.println("Write registration date");
		date = scanner.nextLine();
		System.out.println("Write country of birth");
		country = scanner.nextLine();
		PassportDto dto = PassportDto.builder()
				.name(name)
				.surname(surname)
				.patronymic(patronymic)
				.series(series)
				.number(number)
				.birthYear(age)
				.date(date)
				.birthCountry(country)
				.build();
		template.convertAndSend(fanoutExchange.getName(), "", gson.toJson(dto));
	}

	private Object get(Function<String, ?> function, String name) {
		var isCOrrect = false;
		Object object = null;
		do {
			String a = scanner.nextLine();
			try {
				object = function.apply(a);
				isCOrrect = true;
			} catch (NumberFormatException e) {
				System.err.printf("Incorrect %s. Try again\n", name);
			}
		} while (!isCOrrect);
		return object;
	}
}