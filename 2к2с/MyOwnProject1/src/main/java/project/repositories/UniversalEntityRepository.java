package project.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class UniversalEntityRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> Optional<T> findByName(Class<T> tClass, String name) {
		return entityManager.createQuery(
				"select e from " + tClass.getSimpleName() + " e where e.name = :name", tClass)
				.setParameter("name", name).getResultList().stream().findFirst();
	}

	@Transactional
	public <T> boolean save(T model) {
		entityManager.persist(model);
		return true;
	}

	public <T> Optional<T> find(Class<T> tClass, Long aLong) {
		return Optional.ofNullable(entityManager.find(tClass, aLong));
	}

	@Transactional
	public <T> void delete(Class<T> tClass, Long aLong) {
		find(tClass, aLong).ifPresent(entityManager::remove);
	}

}
