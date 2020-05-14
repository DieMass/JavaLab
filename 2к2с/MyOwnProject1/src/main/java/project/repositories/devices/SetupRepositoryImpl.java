package project.repositories.devices;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.user.Setup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class SetupRepositoryImpl implements SetupRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Setup> getAllByAccount(Long userId) {
		return entityManager.createQuery("select s from Setup s where s.account = :userId",
				Setup.class).setParameter("userId", userId).getResultList();
	}

	@Override
	@Transactional
	public boolean save(Setup model) {
		entityManager.persist(model);
		return entityManager.contains(model);
	}

	@Override
	public int update(Setup model) {
		return 0;
	}

	@Override
	public void delete(Long aLong) {

	}

	@Override
	public Optional<Setup> find(Long aLong) {
		return Optional.of(entityManager.find(Setup.class, aLong));
	}

	@Override
	public List<Setup> findAll() {
		return null;
	}
}
