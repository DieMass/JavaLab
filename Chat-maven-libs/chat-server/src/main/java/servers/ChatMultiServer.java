package die.mass.servers;

import die.mass.DBLoader;
import die.mass.models.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class ChatMultiServer {

    // список клиентов
    private List<ClientHandler> clients;
    private DBLoader dbLoader;

    public ChatMultiServer() {
        // Список для работы с многопоточностью
        clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port, String pathToProperties) {
        ServerSocket serverSocket;
        dbLoader = DBLoader.create(pathToProperties);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // запускаем бесконечный цикл
        while (true) {
            try {
                // запускаем обработчик сообщений для каждого подключаемого клиента
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        // связь с одним клиентом
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private String name;
        private String password;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                boolean f;
                String a = in.readLine();
                if(a.equals("0")) {
                    name = in.readLine();
                    String pas = in.readLine();
                    dbLoader.saveUser(name, pas);
                }
                do {
                    name = in.readLine();
                    password = in.readLine();
                    f = accountExists(name, password);
                    if (f) {
                        out.println("true");
                        System.out.println("New client " + name);
                    } else out.println("false");
                } while (!f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean accountExists(String name, String password) {
            return dbLoader.findUser(name, (long) password.hashCode());
        }

        public void run() {
            try {
                // получем входной поток для конкретного клиента
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        // бегаем по всем клиентам и обовещаем их о событии
                        for (ClientHandler client : clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            out.println("bye");
                        }
                        clients.remove(this);
                        break;
                    } else {
                        for (ClientHandler client : clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            LocalTime localTime = LocalTime.now();
                            out.println(name + " say \"" + inputLine + "\" at " + localTime);
                            dbLoader.saveMessage(inputLine,name,localTime.toString());
                        }
                    }
                }
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
