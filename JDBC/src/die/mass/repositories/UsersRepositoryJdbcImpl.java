package die.mass.repositories;

import die.mass.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private Connection connection;

    //language=SQL
    private final String SQL_INSERT_USER = "insert into " +
            "user_table (firstname, lastname, age, isman) values (?, ?, ?, ?);";
    //language=SQL
    private final String SQL_UPDATE_USER = "update user_table set " +
            "(firstname, lastname, age, isman) = (?, ?, ?, ?) where id = ?;";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    //позволяет преобразовывать строку из бд в объект
    private RowMapper<User> userRowMapper = row -> {
        Long id = row.getLong("id");
        String firstName = row.getString("firstname");
        String lastName = row.getString("lastname");
        Boolean isMan = row.getBoolean("isman");
        Integer age = row.getObject("age", Integer.class);
        return new User(id, firstName, lastName, age, isMan);
    };
    //аналог insert в SQL
    @Override
    public void save(User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setInt(3, model.getAge());
            statement.setBoolean(4, model.getMan());
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
    public void update(User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setInt(3, model.getAge());
            statement.setBoolean(4, model.getMan());
            statement.setLong(5,model.getId());

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
            statement.execute("delete from user_table where id = " + id + ";");
            System.out.println("Deleted is comleted");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //аналог select-а по id SQL
    @Override
    public Optional<User> find(Long id) {
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_table where id = " + id + ";");

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(user);
    }
    //аналог select all в SQL
    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_table");

            while (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
    //аналог select-a по firstname в SQL
    @Override
    public Optional<User> findOneByFirstName(String firstName) {
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user_table where firstname = '" + firstName + "';");

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(user);
    }
}
