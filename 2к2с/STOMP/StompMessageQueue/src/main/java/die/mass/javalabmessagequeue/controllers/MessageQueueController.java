package die.mass.javalabmessagequeue.controllers;

import die.mass.javalabmessagequeue.dto.CreateQueueDto;
import die.mass.javalabmessagequeue.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/queue")
public class MessageQueueController {

	@Autowired
	private QueueService queueService;

	@PostMapping
	public ResponseEntity post(@RequestBody CreateQueueDto dto) {
		System.out.println("here");
		int size = queueService.createQueue(dto.getQueueName());
		return ResponseEntity.ok(size);
	}



}
