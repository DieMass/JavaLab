package die.mass.jlmq;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

public class ProducerMain {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(JlmqClientApplication.class, args);
		JlmqConnector connector = JlmqConnector.builder().port(8082).connect();
		Scanner scanner = new Scanner(System.in);
		JlmqProducer producer = connector.producer().toQueue("test").create();
		String line;
		while (true) {
			line = scanner.nextLine();
				producer.sendMessage(line);
		}
	}
}
