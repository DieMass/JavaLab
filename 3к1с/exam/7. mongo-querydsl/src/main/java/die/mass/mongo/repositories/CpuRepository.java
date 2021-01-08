package die.mass.mongo.repositories;

import die.mass.mongo.models.Cpu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CpuRepository extends MongoRepository<Cpu, ObjectId>, QuerydslPredicateExecutor<Cpu> {

	Optional<Cpu> findBySeries(String series);
//	@RestResource(path = "byCoresCount", rel = "coresCount")
//	@Query(value = "{cores: {$lt: ?0}}")
//	List<Cpu> getAllByCoresLessThan(@Param("cores") Integer count);
}
