package project.repositories.devices;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.devices.cpu.Cpu;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class CpuRepositoryImpl implements CpuRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private final String TABLE_NAME = "Cpu";
	//language=HQL
	private final String SELECT_ALL = "SELECT c FROM " + TABLE_NAME + " c";
	//language=HQL
	private final String SELECT_ALL_ORDER_BY = "SELECT c FROM " + TABLE_NAME + " c order by c.";
	//language=HQL
	private final String UPDATE = "update Cpu c set c.company = :company, c.family = :family, " +
			"c.line = :line, c.socket = :socket, c.cores = :cores, c.threads = :threads, " +
			"c.series = :series, c.baseClock = :baseClock, c.lithography = :lithography, " +
			"c.maxClockSingleCore = :maxClockSingleCore, c.tdp = :tdp where c.id = :id";

	@Override
	@Transactional
	public boolean save(Cpu model) {
		entityManager.persist(model);
		return find(model.getId()).isPresent();
	}

	@Override
	@Transactional
	public int update(Cpu model) {
		Cpu cpu = entityManager.find(Cpu.class, model.getId());
		return entityManager.createQuery(UPDATE).setParameter("id", model.getId())
				.setParameter("company", getOneNotNull(model.getCompany(), cpu.getCompany()))
				.setParameter("family", getOneNotNull(model.getFamily(), cpu.getFamily()))
				.setParameter("line", getOneNotNull(model.getLine(), cpu.getLine()))
				.setParameter("socket", getOneNotNull(model.getSocket(), cpu.getSocket()))
				.setParameter("cores", getOneNotNull(model.getCores(), cpu.getCores()))
				.setParameter("threads", getOneNotNull(model.getThreads(), cpu.getThreads()))
				.setParameter("series", getOneNotNull(model.getSeries(), cpu.getSeries()))
				.setParameter("baseClock", getOneNotNull(model.getBaseClock(), cpu.getBaseClock()))
				.setParameter("lithography", getOneNotNull(model.getLithography(), cpu.getLithography()))
				.setParameter("maxClockSingleCore", getOneNotNull(model.getMaxClockSingleCore(), cpu.getMaxClockSingleCore()))
				.setParameter("tdp", getOneNotNull(model.getTdp(), cpu.getTdp()))
				.executeUpdate();
	}

	private <T> T getOneNotNull(T modelField, T cpuField) {
		return modelField == null ? cpuField : modelField;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Optional<Cpu> cpuCandidate = find(id);
		if (cpuCandidate.isPresent()) {
//			entityManager.remove(cpuCandidate.get());
			Query query = entityManager.createQuery("DELETE FROM Cpu c WHERE c.id = :id").setParameter("id", id);
			System.out.println(query.executeUpdate());
		} else {
			System.out.println("WyccTo");
		}
	}

	@Override
	@Transactional
	public Optional<Cpu> find(Long aLong) {
		return Optional.ofNullable(entityManager.find(Cpu.class, aLong));
	}

	@Override
	public List<Cpu> findAll() {
		return entityManager.createQuery(SELECT_ALL, Cpu.class).getResultList();
	}

	@Override
	@Transactional
	public List<Cpu> findAllWithPagination(Integer size, Integer page, String sort) {
		Query query = entityManager.createQuery(SELECT_ALL_ORDER_BY + sort, Cpu.class);
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.getResultList();
	}

	@Override
	public List<Cpu> findBySocket(String socketName) {
		return entityManager.createQuery("select m from Cpu m where m.socket.name = :socketName", Cpu.class)
				.setParameter("socketName", socketName).getResultList();
	}
}
