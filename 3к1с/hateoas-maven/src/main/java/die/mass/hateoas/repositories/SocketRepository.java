package die.mass.hateoas.repositories;

import die.mass.entities.adapters.Socket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRepository extends JpaRepository<Socket, Long> {
}
