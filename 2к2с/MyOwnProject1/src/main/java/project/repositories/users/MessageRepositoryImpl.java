package project.repositories.users;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.dto.MessageAdminListDto;
import project.models.user.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MessageRepositoryImpl implements MessageRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean save(Message model) {
		entityManager.persist(model);
		return find(model.getId()).isPresent();
	}

	@Override
	public int update(Message model) {
		return 0;
	}

	@Override
	public void delete(Long aLong) {

	}

	@Override
	public Optional<Message> find(Long aLong) {
		return Optional.of(entityManager.find(Message.class, aLong));
	}

	@Override
	public List<Message> findAll() {
		return entityManager.createQuery("select m from Message m", Message.class).getResultList();
	}

	@Override
	public List<Message> getMessagesByUser(Long userId) {
		return entityManager.createQuery("select m from Message m where m.receiver = :id or m.sender = :id", Message.class)
				.setParameter("id", userId).getResultList();
	}

	private String SQL = "select account.id, account.name, m.text " +
			"from (select distinct s.account, max(s.d) maxdate " +
			"      from (select m.sender account, m.datetime d " +
			"            from message m " +
			"            union " +
			"            select m.receiver, m.datetime " +
			"            from message m) s " +
			"      group by s.account) s2 " +
			"         inner join message m on s2.maxdate = m.datetime " +
			"         inner join account on s2.account = account.id order by account.id;";

	@Override
	public List<MessageAdminListDto> getAllUsers() {
		List<Object[]> list = entityManager.createNativeQuery(SQL).getResultList();
		list.forEach(i -> System.out.println(i[0] + " " + i[1]));
		List<MessageAdminListDto> answer = list.stream().map(i -> MessageAdminListDto.builder()
				.id(((BigInteger)i[0]).longValue()).name((String) i[1]).text((String) i[2])
				.build()).filter(dto -> !dto.getId().equals(1L)).collect(Collectors.toList());
		return answer;
	}
}
