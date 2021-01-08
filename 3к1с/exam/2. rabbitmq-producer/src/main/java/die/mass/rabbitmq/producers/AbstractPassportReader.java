package die.mass.rabbitmq.producers;

import com.google.gson.Gson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public abstract class AbstractPassportReader implements PasstortReader {

	protected Scanner scanner;
	protected AmqpTemplate template;
	protected Gson gson;
	protected FanoutExchange fanoutExchange;
}
