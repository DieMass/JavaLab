package die.mass.repositories;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryJdbcImpl implements MessageRepository {
    private Connection connection;

    //language=SQL
    private final String SQL_INSERT_USER = "insert into " +
            "message (content, name, time) values (?,?,?);";
    //language=SQL
    private final String SQL_UPDATE_USER = "update message set " +
            "(content,name,time) = (?,?,?) where id = ?;";

    public MessageRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    //позволяет преобразовывать строку из бд в объект
    private RowMapper<die.mass.models.Message> userRowMapper = row -> {
        Long id = row.getLong("id");
        String content = row.getString("content");
        String name = row.getString("name");
        String time = row.getString("time");
        return new die.mass.models.Message(id, content,name,time);
    };
    //аналог insert в SQL
    @Override
    public void save(die.mass.models.Message model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getContent());
            statement.setString(2, model.getName());
            statement.setString(3, model.getTime());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }

            ResultSet generatesKeys = statement.getGeneratedKeys();

            if (generatesKeys.next()) {
                model.setId(generatesKeys.getLong("id"));
            } else {
                throw new SQLException();
            }
            statement.close();
            generatesKeys.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //аналог update в SQL
    @Override
    public void update(die.mass.models.Message model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getContent());
            statement.setLong(2,model.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //аналог delete в SQL
    @Override
    public void delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete from account where id = " + id + ";");
            System.out.println("Deleted is comleted");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //аналог select-а по id SQL
    @Override
    public Optional<die.mass.models.Message> find(Long id) {
        die.mass.models.Message user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from message where id = " + id + ";");

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<die.mass.models.Message> findAll() {
        List<die.mass.models.Message> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from message");

            while (resultSet.next()) {
                die.mass.models.Message user = userRowMapper.mapRow(resultSet);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
