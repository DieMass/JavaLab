package project.repositories.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import project.models.user.Image;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ImageRepositoryImpl implements ImageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = " image ";
    //language=SQL
    private final String SQL_INSERT_IMAGE = "INSERT INTO " + TABLE_NAME + " (name) VALUES (?);";
    //language=SQL
//    private final String SQL_UPDATE_USER = "UPDATE " + TABLE_NAME + " SET " + "(name, password) = (?, ?) WHERE id = ?;";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME + ";";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
    //language=SQL
    private final String SQL_SELECT_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?;";
    //language=SQL
    private final String SQL_DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";

    private RowMapper<Image> imageRowMapper = (row, i) -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        return Image.builder()
                .id(id).name(name).build();
    };
    @Override
    public boolean save(Image model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT_IMAGE);
            statement.setString(1, model.getName());
            return statement;
        }, keyHolder);
        model.setId((Long)keyHolder.getKey());
        return true;
    }

    @Override
    public int update(Image model) {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Image> find(Long id) {
        try {
            Image image = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, imageRowMapper);
            return Optional.ofNullable(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Image> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, imageRowMapper);
    }

    @Override
    public Optional<Image> findByName(String name) {
        try {
            Image image = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new Object[]{name} , imageRowMapper);
            return Optional.ofNullable(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
