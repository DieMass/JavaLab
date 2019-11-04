package main.java.die.mass;

import main.java.die.mass.models.Image;
import main.java.die.mass.repositories.ImageRepository;
import main.java.die.mass.repositories.ImageRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ImageLoaderDB {

    Connection connection;
    ImageRepository imagesRepository;

    public static ImageLoaderDB create(String properties) {
        return new ImageLoaderDB(properties);
    }

    private ImageLoaderDB(String properties) {
        this.connection = connect(properties);
        this.imagesRepository = new ImageRepositoryJdbcImpl(connection);
    }

    public void save(Image image) {
        imagesRepository.save(image);
    }

    public List getAll() {
        return imagesRepository.findAll();
    }

    private Connection connect(String propertiesName) {
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String url = properties.getProperty("db.url");

        Connection connection;

        try{
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }
}
