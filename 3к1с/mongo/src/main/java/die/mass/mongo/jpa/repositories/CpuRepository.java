package die.mass.mongo.jpa.repositories;

import die.mass.mongo.models.Cpu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface CpuRepository extends MongoRepository<Cpu, String> {

	Optional<Cpu> findBySeries(String series);

	@RestResource(path = "byCoresCount", rel = "coresCount")
	@Query(value = "{cores: {$lt: ?0}}")
	List<Cpu> getAllByCoresLessThan(@Param("cores") Integer count);
}
