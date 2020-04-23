package project.repositories;

import org.springframework.stereotype.Component;
import project.models.Chat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ChatRepositoryImpl implements ChatRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Chat> getChatsById(Long userId) {
		return entityManager.createQuery("select c from Chat c where c.users in (select u from User u where u.id = :userId)", Chat.class)
				.setParameter("userId", userId).getResultList();
	}

	@Override
	public List<Chat> getAllChats() {
		return entityManager.createQuery("select c from Chat c", Chat.class).getResultList();
	}

	@Override
	public boolean save(Chat model) {
		entityManager.persist(model);
		return true;
	}

	@Override
	public Chat find(Long aLong) {
		return entityManager.find(Chat.class, aLong);
	}

	@Override
	public Chat update(Long aLong, Chat model) {
		return null;
	}

	@Override
	public boolean remove(Long aLong) {
		return false;
	}
}
