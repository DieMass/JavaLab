package die.mass.mongo.repositories;

import die.mass.mongo.models.Socket;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocketRepository extends MongoRepository<Socket, ObjectId> {
}
