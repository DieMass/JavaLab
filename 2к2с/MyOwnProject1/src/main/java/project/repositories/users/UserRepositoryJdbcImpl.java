package project.repositories.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import project.models.user.Role;
import project.models.user.State;
import project.models.user.User;
import project.repositories.general.CrudRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJdbcImpl implements UserRepository, CrudRepository<User, Long> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	private final String TABLE_NAME = " account ";
	//language=SQL
	private final String SQL_INSERT_USER =
			"INSERT INTO " + TABLE_NAME + " (name, email , password, token) VALUES (?, ?, ?, ?);";
	//language=SQL
	private final String SQL_UPDATE_USER = "UPDATE " + TABLE_NAME + " SET " + "(name, password) = (?, ?) WHERE id = ?;";
	//language=SQL
	private final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME + ";";
	//language=SQL
	private final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
	//language=SQL
	private final String SQL_SELECT_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?;";
	//language=SQL
	private final String SQL_SELECT_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?;";
	//language=SQL
	private final String SQL_VERIFY_BY_NAME = "UPDATE " + TABLE_NAME + " SET state = 'CONFIRMED' WHERE name = ?;";
	//language=SQL
	private final String SQL_DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";

	public UserRepositoryJdbcImpl() {
	}

	//позволяет преобразовывать строку из бд в объект
	private RowMapper<User> userRowMapper = (row, i) -> {
		Long id = row.getLong("id");
		String name = row.getString("name");
		String password = row.getString("password");
		String email = row.getString("email");
		String token = row.getString("token");
		Role role = Enum.valueOf(Role.class, row.getString("role"));
		State state = Enum.valueOf(State.class, row.getString("state"));
		return User.builder()
				.id(id).name(name).email(email).password(password)
				.token(token).role(role).state(state).build();
	};

	@Override
	public boolean save(User model) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement(SQL_INSERT_USER);
			statement.setString(1, model.getName());
			statement.setString(2, model.getEmail());
			statement.setString(3, model.getPassword());
			statement.setString(4, model.getToken());
			return statement;
		}, keyHolder);
		model.setId((Long)keyHolder.getKey());
		System.out.println(model.getId());
		return true;
	}

	@Override
	public int update(User model) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement(SQL_UPDATE_USER);
			statement.setString(1, model.getName());
			statement.setString(2, model.getPassword());
			statement.setLong(3, model.getId());
			return statement;
		}, keyHolder);
		model.setId((Long)keyHolder.getKey());
		return 1;
	}

	//аналог delete в SQL
	@Override
	public void delete(Long id) {
		getJdbcTemplate().update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement(SQL_DELETE_BY_ID);
			statement.setLong(1, id);
			return statement;
		});
//        getJdbcTemplate().update(SQL_DELETE_BY_ID, id);
	}

	@Override
	public Optional<User> find(Long id) {
		try {
			User user = getJdbcTemplate().queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, userRowMapper);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<User> findAll() {
		return getJdbcTemplate().query(SQL_SELECT_ALL, userRowMapper);
	}

	@Override
	public Optional<User> findByName(String name) {
		try {
			User user = getJdbcTemplate().queryForObject(SQL_SELECT_BY_NAME, new Object[]{name} ,userRowMapper);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		try {
			User user = getJdbcTemplate().queryForObject(SQL_SELECT_BY_EMAIL, new Object[]{email} ,userRowMapper);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void verifyUser(String name) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement(SQL_VERIFY_BY_NAME);
			statement.setString(1, name);
			return statement;
		}, keyHolder);
	}
}
