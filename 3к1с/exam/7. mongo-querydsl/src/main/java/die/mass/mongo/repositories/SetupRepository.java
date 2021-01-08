package die.mass.mongo.repositories;

import die.mass.mongo.models.Setup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SetupRepository extends MongoRepository<Setup, String> {
}
