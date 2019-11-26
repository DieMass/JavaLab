package die.mass.servers;

import die.mass.protocol.Protocol;
import die.mass.repositories.*;
import die.mass.services.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ServerContext {

    private GetTokenServiceImpl getTokenService;
    private LoginService loginService;
    private ResponseService responseService;
    private MessageService messageService;
    private CommandService commandService;
    private AdminService adminService;
    private Connection connection;
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private GoodRepository goodRepository;
    private Protocol protocolIn = new Protocol();
    private Protocol protocolOut = new Protocol();

    public ServerContext(String properties, List<ChatMultiServer.ClientHandler> clients) {
        this.getTokenService = new GetTokenServiceImpl("qwerty007");
        this.connection = connect(properties);
        this.userRepository = new UserRepositoryJdbcImpl(connection);
        this.messageRepository = new MessageRepositoryJdbcImpl(connection);
        this.goodRepository = new GoodRepositoryJdbcImpl(connection);
        this.loginService = new LoginService(userRepository, protocolIn, protocolOut, getTokenService);
        this.messageService = new MessageService(messageRepository, getTokenService);
        this.responseService = new ResponseService(clients);
        this.adminService = new AdminService(getTokenService);
        this.commandService = new CommandService(messageRepository,goodRepository, adminService);
    }

    private Connection connect(String propertiesName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propertiesName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String url = properties.getProperty("db.url");

        Connection connection;

        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public ResponseService getResponseService() {
        return responseService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public Protocol getProtocolIn() {
        return protocolIn;
    }

    public Protocol getProtocolOut() {
        return protocolOut;
    }

    public GetTokenServiceImpl getGetTokenService() {
        return getTokenService;
    }
}
