package die.mass.javalabmessagequeue.services;

import die.mass.javalabmessagequeue.repositories.TaskRepository;
import die.mass.javalabmessagequeue.server.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public void save(Task task) {
		taskRepository.save(task);
	}

	public void update(Task task) {
		taskRepository.findById(task.getId());
		taskRepository.save(task);
	}
}
