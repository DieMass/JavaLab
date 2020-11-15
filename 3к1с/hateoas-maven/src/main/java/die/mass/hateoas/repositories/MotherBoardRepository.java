package die.mass.hateoas.repositories;

import die.mass.entities.adapters.Socket;
import die.mass.entities.motherboard.MotherBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotherBoardRepository extends JpaRepository<MotherBoard, Long> {
}
