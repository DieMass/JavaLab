package die.mass.jlmq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayDeque;
import java.util.Map;

@Component
@Getter
public class JavaLabMessageQueue {

	public JlmqConnector.JlmqConnectorBuilder connector() {
		return JlmqConnector.builder();
	}

}
