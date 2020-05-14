package project.repositories.devices;

import org.springframework.stereotype.Component;
import project.repositories.general.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public abstract class RepositoryImpl<T, ID> implements CrudRepository<T, ID> {

	protected String tableName;
	protected String SELECT_ALL_BY_NAME = "select m from %s m where name = :name";
	protected String SELECT_ALL = "select m from %s m";

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public boolean save(T model) {
		entityManager.persist(model);
		return entityManager.contains(model);
	}

	@Override
	public int update(T model) {
		return 0;
	}

	@Override
	public void delete(ID id) {

	}
}
