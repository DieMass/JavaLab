package die.mass.rabbitmq;

import com.google.gson.Gson;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import die.mass.rabbitmq.consumers.SecondListener;
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

@SpringBootApplication
@EnableRabbit
public class RabbitmqApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(RabbitmqApplication.class, args);
		var reader = context.getBean(PassportConsoleReader.class);
		var secondListener = context.getBean(SecondListener.class);
		secondListener.listen();
		reader.read();
	}

	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange("passports");
	}

	@Bean
	public Queue firstQueue() {
		return new Queue("firstQueue");
	}

	@Bean
	public Queue secondQueue() {
		return new Queue("secondQueue");
	}

	@Bean
	public Queue thirdQueue() {
		return new Queue("thirdQueue");
	}

	@Bean
	public Binding binding1(FanoutExchange fanout, Queue firstQueue) {
		return BindingBuilder.bind(firstQueue).to(fanout);
	}

	@Bean
	public Binding binding2(FanoutExchange fanout, Queue secondQueue) {
		return BindingBuilder.bind(secondQueue).to(fanout);
	}

	@Bean
	public Binding binding3(FanoutExchange fanout, Queue thirdQueue) {
		return BindingBuilder.bind(thirdQueue).to(fanout);
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
