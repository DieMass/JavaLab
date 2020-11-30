package die.mass.mongo.jpa.repositories;

import die.mass.mongo.models.MotherBoard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MotherBoardRepository extends MongoRepository<MotherBoard, String> {
}
