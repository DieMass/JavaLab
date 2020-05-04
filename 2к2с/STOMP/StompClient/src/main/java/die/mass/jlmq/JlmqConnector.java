package die.mass.jlmq;

import lombok.Builder;

@Builder(buildMethodName = "connect")
public class JlmqConnector {

	private int port;

	public JlmqProducer.Builder producer() {
		return JlmqProducer.builder(port);
	}

	public JlmqConsumer.Builder consumer() {
		return JlmqConsumer.builder(port);
	}
}
