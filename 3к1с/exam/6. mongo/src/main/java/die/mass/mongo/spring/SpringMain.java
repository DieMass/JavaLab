package die.mass.mongo.spring;

import die.mass.mongo.models.Cpu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class SpringMain {
	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext(SimpleMongoConfig.class);
		var mongoTemplate = context.getBean(MongoTemplate.class);
		var cpu = Cpu.builder().series("237best").cores(20).build();
		mongoTemplate.save(cpu, "cpu");
		var query = new Query(Criteria.where("series").is("237best"));
		var update = new Update().set("series", "236best");
		mongoTemplate.upsert(query, update, "cpu");
		var queryDelete = new Query(Criteria.where("series").is("236best"));
		mongoTemplate.remove(queryDelete, "cpu");
	}
}
