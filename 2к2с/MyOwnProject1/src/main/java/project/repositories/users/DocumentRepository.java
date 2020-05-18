package project.repositories.users;

import org.springframework.data.repository.CrudRepository;
import project.models.user.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {
}
