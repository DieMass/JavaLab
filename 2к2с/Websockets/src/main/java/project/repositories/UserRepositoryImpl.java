package project.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean save(User model) {
		entityManager.persist(model);
		return true;
	}

	@Override
	public User find(Long aLong) {
		return entityManager.find(User.class, aLong);
	}

	@Override
	public User update(Long aLong, User model) {
		return null;
	}

	@Override
	public boolean remove(Long aLong) {
		entityManager.remove(find(aLong));
		return true;

	}

	@Override
	public Optional<User> findByEmail(String email) {
		return entityManager.createQuery("select u from User u where u.email = :email", User.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}
}
