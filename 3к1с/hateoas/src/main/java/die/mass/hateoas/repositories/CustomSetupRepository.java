package die.mass.hateoas.repositories;

import die.mass.hateoas.entities.CustomSetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomSetupRepository extends JpaRepository<CustomSetup, Long> {
}
