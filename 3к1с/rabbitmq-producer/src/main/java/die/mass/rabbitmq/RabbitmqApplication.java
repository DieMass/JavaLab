package die.mass.rabbitmq;

import com.google.gson.Gson;
import die.mass.rabbitmq.consumers.SecondFanoutListener;
import die.mass.rabbitmq.producers.PassportConsoleReader;
import die.mass.rabbitmq.producers.PassportFileReader;
import lombok.SneakyThrows;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

@SpringBootApplication
@EnableRabbit
public class RabbitmqApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(RabbitmqApplication.class, args);
		var fileReader = context.getBean(PassportFileReader.class);
		var consoleReader = context.getBean(PassportConsoleReader.class);
		var secondListener = context.getBean(SecondFanoutListener.class);
//		while (true) fileReader.read();
	}

	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange("fanout");
	}

	@Bean
	public DirectExchange direct() { return new DirectExchange("direct"); }

	@Bean
	public TopicExchange topic() { return new TopicExchange("topic"); }

	@Bean
	public TopicExchange passportTopic() { return new TopicExchange("passportTopic"); }

	@Bean
	public Queue firstFanoutQueue() {
		return new Queue("firstFanoutQueue");
	}

	@Bean
	public Queue secondFanoutQueue() {
		return new Queue("secondFanoutQueue");
	}

	@Bean
	public Queue russianPassportQueue() {
		return new Queue("russianPassportQueue");
	}

	@Bean
	public Queue kazakhstanPassportQueue() {
		return new Queue("kazakhstanPassportQueue");
	}

	@Bean
	public Queue confirmationTopicQueue() {
		return new Queue("confirmationTopicQueue");
	}

	@Bean
	public Binding binding1QueueToFanout() {
		return BindingBuilder.bind(firstFanoutQueue()).to(fanout());
	}

	@Bean
	public Binding bindingPassportTopicToFanout() {
		return BindingBuilder.bind(passportTopic()).to(fanout());
	}

	@Bean
	public Binding bindingTopicToPassportTopic() {
		return BindingBuilder.bind(topic()).to(passportTopic()).with("passport.#");
	}

	@Bean
	public Binding bindingConfirmationToTopic() {
		return BindingBuilder.bind(confirmationTopicQueue()).to(topic()).with("*.not_confirmed.*");
	}

	@Bean
	public Binding bindingDirectToTopic() {
		return BindingBuilder.bind(direct()).to(topic()).with("*.confirmed.*");
	}

	@Bean
	public Binding bindingRussianQueueToDirect() {
		return BindingBuilder.bind(russianPassportQueue()).to(direct()).with("passport.confirmed.РФ");
	}

	@Bean
	public Binding bindingKazakhstanQueueToDirect() {
		return BindingBuilder.bind(kazakhstanPassportQueue()).to(direct()).with("passport.confirmed.Казахстан");
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}

	@Bean
	public com.rabbitmq.client.ConnectionFactory connectionFactory() {
		var factory = new com.rabbitmq.client.ConnectionFactory();
		factory.setHost("localhost");
		return factory;
	}

	@Bean
	public ConnectionFactory springConnectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(springConnectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(springConnectionFactory());
	}

	@Bean
	public Scanner consoleScanner() {
		return new Scanner(System.in);
	}

	@SneakyThrows
	@Bean
	public Scanner fileScanner() {
		return new Scanner(new FileInputStream(new File("input.txt")));
	}

//	@RabbitListener(queues = "passport")
//	public void secondListener(Message failedMessage) {
//		System.out.println("hi from second");
//		// Save to DB or send a notification.
//	}

//	@Bean
//	public SimpleMessageListenerContainer messageListenerContainer1() {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//		container.setConnectionFactory(connectionFactory());
//		container.setQueueNames(queueName());
//		container.setMessageListener(message -> System.out.printf("received from %s : %s\n", queueName(), new String(message.getBody())));
//		return container;
//	}
}
