package die.mass.javalabmessagequeue.repositories;

import die.mass.javalabmessagequeue.server.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
