package die.mass.javalabmessagequeue.services;

import die.mass.javalabmessagequeue.server.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

@Service
public class QueueService {

	@Autowired
	@Qualifier("queues")
	private Map<String, Queue<Message>> map;

	public int createQueue(String name) {
		map.put(name, new ArrayDeque<>());
		return map.keySet().size();
	}
}
