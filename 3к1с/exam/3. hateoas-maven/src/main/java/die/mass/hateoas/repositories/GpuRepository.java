package die.mass.hateoas.repositories;

import die.mass.entities.gpu.GraphicsCardBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuRepository extends JpaRepository<GraphicsCardBase, Long> {
}
