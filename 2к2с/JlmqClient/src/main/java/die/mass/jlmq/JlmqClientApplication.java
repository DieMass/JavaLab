package die.mass.jlmq;

import die.mass.jlmq.protocol.Command;
import die.mass.jlmq.protocol.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.objenesis.SpringObjenesis;

import java.util.Scanner;

@SpringBootApplication
public class JlmqClientApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(JlmqClientApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		JlmqConnector connector = JlmqConnector.builder().port(8082).connect();
		JlmqProducer producer = connector.producer().toQueue("test").create();
		JlmqConsumer consumer;
		String line;
		while (true) {
			line = scanner.nextLine();
			if (line.equals("consumer")) {
				consumer = connector.consumer().subscribe("test").create();
			} else {
				producer.sendMessage(line);
			}
		}

	}

}
