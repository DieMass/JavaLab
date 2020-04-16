package project.repositories.users;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.user.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ImageRepositoryJpaImpl implements ImageRepository {

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Optional<Image> findByName(String name) {
		return Optional.empty();
	}

	@Override
	@Transactional
	public boolean save(Image model) {
		entityManager.persist(model);
		return find(model.getId()).isPresent();
	}

	@Override
	public int update(Image model) {
		return 0;
	}

	@Override
	public void delete(Long aLong) {

	}

	@Override
	public Optional<Image> find(Long aLong) {
		return Optional.of(entityManager.find(Image.class, aLong));
	}

	@Override
	public List<Image> findAll() {
//		Query query = entityManager.createQuery("SELECT f FROM File f", Image.class);

		return new ArrayList<>();
	}
}
