package main.java.die.mass.repositories;

import main.java.die.mass.models.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryJdbcImpl implements ImageRepository {
    private Connection connection;

    //language=SQL
    private final String SQL_INSERT_USER = "insert into " +
            "image (name, size) values (?, ?);";
    //language=SQL
    private final String SQL_UPDATE_USER = "update image set " +
            "(name, size) = (?, ?) where id = ?;";

    public ImageRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }
    //позволяет преобразовывать строку из бд в объект
    private RowMapper<Image> userRowMapper = row -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        Long size = row.getLong("size");
        return new Image(id, name, size);
    };
    //аналог insert в SQL
    @Override
    public void save(Image model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setLong(2, model.getSize());

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
    public void update(Image model) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setLong(2, model.getSize());
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
            statement.execute("delete from image where id = " + id + ";");
            System.out.println("Deleted is comleted");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //аналог select-а по id SQL
    @Override
    public Optional<Image> find(Long id) {
        Image image = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from image where id = " + id + ";");

            if (resultSet.next()) {
                image = userRowMapper.mapRow(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(image);
    }

    @Override
    public List<Image> findAll() {
        List<Image> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from image");

            while (resultSet.next()) {
                Image image = userRowMapper.mapRow(resultSet);
                result.add(image);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
