package die.mass.javalabmessagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import die.mass.javalabmessagequeue.server.ConsumerThread;
import die.mass.javalabmessagequeue.server.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@SpringBootApplication
public class JavaLabMessageQueueApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(JavaLabMessageQueueApplication.class, args);
	}

	@Bean
	public Map<String, Queue<Message>> queues() {
		return new HashMap<>();
	}

	@Bean
	public  Map<String, WebSocketSession> sessions() {
		return new HashMap<>();
	}

	@Bean
	public Map<String, ConsumerThread> consumerThreads() {
		return new HashMap<>();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}

