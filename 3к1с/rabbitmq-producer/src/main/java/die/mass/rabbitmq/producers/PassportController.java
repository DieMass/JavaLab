package die.mass.rabbitmq.producers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class PassportController {

	private AmqpTemplate template;

	@PostMapping("/api")
	public @ResponseBody
	String read(@RequestBody String json) {
		System.out.println(json);
		return "";
	}

}
