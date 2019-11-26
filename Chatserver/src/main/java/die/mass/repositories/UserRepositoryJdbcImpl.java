package die.mass.repositories;

import die.mass.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    //language=SQL
    private final String SQL_INSERT_USER = "insert into " +
            "account (name, password) values (?, ?);";
    //language=SQL
    private final String SQL_UPDATE_USER = "update account set " +
            "(name, password) = (?, ?) where id = ?;";

    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    //позволяет преобразовывать строку из бд в объект
    private RowMapper<die.mass.models.User> userRowMapper = row -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        String password = row.getString("password");
        return new die.mass.models.User(id, name, password);
    };
    //аналог insert в SQL
    @Override
    public boolean save(die.mass.models.User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setString(2, model.getPassword());

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
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //аналог update в SQL
    @Override
    public void update(die.mass.models.User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setString(2, model.getPassword());
            statement.setLong(3,model.getId());

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
    public Optional<die.mass.models.User> find(Long id) {
        die.mass.models.User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account where id = " + id + ";");

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
    public List<die.mass.models.User> findAll() {
        List<die.mass.models.User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account");

            while (resultSet.next()) {
                die.mass.models.User user = userRowMapper.mapRow(resultSet);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public String contains(String name) {
        die.mass.models.User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account where name = '" + name + "';");

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return user == null ? null : user.getPassword();
    }
}
