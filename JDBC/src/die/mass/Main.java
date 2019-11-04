package die.mass;

import die.mass.models.User;
import die.mass.repositories.UsersRepository;
import die.mass.repositories.UsersRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
       Properties properties = new Properties();
        try {
            properties.load(new FileReader("db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String url = properties.getProperty("db.url");

        Connection connection;

        try{
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(connection);

        Optional<User> userCandidate = usersRepository.find(2L);

        if(userCandidate.isPresent()) {
            System.out.println(userCandidate.get());
        } else {
            System.out.println( "Ничего не найдено");
        }

//        usersRepository.findAll().forEach(element -> System.out.println(element));
//        usersRepository.delete(2L);
        usersRepository.save(new User(null,"щуп","фыа",12,false));
//        usersRepository.update(new User(9L,"щуп2","фыа2",123,true));
         userCandidate = usersRepository.findOneByFirstName("Jesus");

        if(userCandidate.isPresent()) {
            System.out.println(userCandidate.get());
        } else {
            System.out.println( "Ничего не найдено");
        }
    }

}
