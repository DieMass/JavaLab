package die.mass.hateoas.repositories;

import die.mass.entities.cpu.Cpu;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CpuRepository extends PagingAndSortingRepository<Cpu, Long> {

	@RestResource(path = "bySeries", rel = "series")
	List<Cpu> findAllBySeries(String series);
}
