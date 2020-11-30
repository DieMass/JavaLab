package die.mass.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(MongoApplication.class, args);

	}

}
