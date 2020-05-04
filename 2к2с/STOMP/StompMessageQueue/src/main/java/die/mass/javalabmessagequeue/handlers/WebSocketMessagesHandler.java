//package die.mass.javalabmessagequeue.handlers;
//
//import com.google.gson.Gson;
//import die.mass.javalabmessagequeue.Command;
//import die.mass.javalabmessagequeue.server.ConsumerThread;
//import die.mass.javalabmessagequeue.server.Message;
//import die.mass.javalabmessagequeue.server.Task;
//import die.mass.javalabmessagequeue.services.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayDeque;
//import java.util.Map;
//
//@Component
//@EnableWebSocket
//public class WebSocketMessagesHandler extends TextWebSocketHandler {
//
//	@Autowired
//	private Gson gson;
//	@Autowired
//	@Qualifier("consumerThreads")
//	private Map<String, ConsumerThread> consumerThreads;
//	@Autowired
//	private TaskService taskService;
//
//
//	@Override
//	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//		String messageText = (String) message.getPayload();
//		Message mes = gson.fromJson(messageText, Message.class);
//		System.out.println("WSMH.hM() " + messageText);
//		switch (mes.getCommand()) {
//			case subscribe:
//				if (!consumerThreads.containsKey(mes.getQueueName())) {
//					consumerThreads.put(mes.getQueueName(),
//							ConsumerThread.builder().queue(new ArrayDeque<>()).session(session).build());
//					consumerThreads.get(mes.getQueueName()).start();
//				}
//				consumerThreads.get(mes.getQueueName()).setSession(session);
//				synchronized (consumerThreads.get(mes.getQueueName()).getQueue()) {
//					consumerThreads.get(mes.getQueueName()).getQueue().notify();
//				}
//				break;
//		}
//	}
//}
//
