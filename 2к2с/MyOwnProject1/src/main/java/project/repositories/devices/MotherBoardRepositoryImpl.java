package project.repositories.devices;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.devices.motherboard.MotherBoard;

import java.util.List;
import java.util.Optional;

@Component
public class MotherBoardRepositoryImpl extends RepositoryImpl<MotherBoard, Long> implements MotherBoardRepository {

	private String table = "MotherBoard";

	@Override
	@Transactional
	public boolean save(MotherBoard model) {
		entityManager.persist(model);
		return entityManager.contains(model);
	}

	@Override
	public Optional<MotherBoard> find(Long aLong) {
		return Optional.of(entityManager.find(MotherBoard.class, aLong));
	}


	public List<MotherBoard> findByName(String name) {
		return entityManager.createQuery(String.format(SELECT_ALL_BY_NAME, table), MotherBoard.class)
				.setParameter("name", name).getResultList();
	}

	public List<MotherBoard> findBySocket(String socketName) {
		return entityManager.createQuery("select m from MotherBoard m where m.socket.name = :socketName", MotherBoard.class)
				.setParameter("socketName", socketName).getResultList();
	}

	@Override
	public List<MotherBoard> findAll() {
		return entityManager.createQuery(String.format(SELECT_ALL, table), MotherBoard.class).getResultList();
//		return entityManager.createQuery("select m from MotherBoard m", MotherBoard.class).getResultList();
	}
}
