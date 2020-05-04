package die.mass.jlmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class JlmqClientApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(JlmqClientApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Write queueName:");
		String queueName = scanner.nextLine();
		JlmqConnector connector = JlmqConnector.builder().port(8082).connect();
		JlmqProducer producer = connector.producer().toQueue(queueName).create();
		JlmqConsumer consumer;
		String line;
		while (true) {
			line = scanner.nextLine();
			if (line.equals("consumer")) {
				consumer = connector.consumer().subscribe(queueName).create();
			} else {
				producer.sendMessage(line);
			}
		}

	}

}
