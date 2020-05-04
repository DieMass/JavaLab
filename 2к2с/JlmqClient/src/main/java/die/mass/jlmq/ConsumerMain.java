package die.mass.jlmq;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class ConsumerMain {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(JlmqClientApplication.class, args);
		JlmqConnector connector = JlmqConnector.builder().port(8082).connect();
		JlmqConsumer consumer = connector.consumer().subscribe("test").create();

	}
}
