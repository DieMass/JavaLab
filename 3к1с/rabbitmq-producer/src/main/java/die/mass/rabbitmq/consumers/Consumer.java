package die.mass.rabbitmq.consumers;

import org.springframework.amqp.core.Message;

public interface Consumer {

	void listen(Message message);

}
