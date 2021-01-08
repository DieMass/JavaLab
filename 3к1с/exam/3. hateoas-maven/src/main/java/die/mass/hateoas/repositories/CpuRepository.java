package die.mass.hateoas.repositories;

import die.mass.entities.cpu.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.awt.print.Pageable;
import java.util.List;

@RepositoryRestResource
public interface CpuRepository extends PagingAndSortingRepository<Cpu, Long> {
	@RestResource(path = "bySeries", rel = "series")
	Cpu findBySeries(String series);
}
