package die.mass.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;


public class SocketClient {
    // поле, содержащее сокет-клиента
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    private String password;

    // начало сессии - получаем ip-сервера и его порт
    public void startConnection(String ip, int port) {
        try {

            // создаем подключение
            clientSocket = new Socket(ip, port);
            // получили выходной поток
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // входной поток
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean f = false;
            Scanner scanner = new Scanner(System.in);
            do {
                f = true;
                System.out.println("Зарегистрироваться (0) или войти (1) ?");
                String a = scanner.nextLine();
                out.println(a);
                if (a.equals("1")) {
                } else if (a.equals("0")) {
                    System.out.print("Print name: ");
                    out.println(scanner.nextLine());
                    System.out.print("Print password: ");
                    out.println(scanner.nextLine());
                } else {
                    System.err.println("I DONT UNDERSTAND");
                    f = false;
                }
            } while (!f);
            f = false;
            do {
                System.out.print("Print name: ");
                name = scanner.nextLine();
                System.out.print("Print password: ");
                password = scanner.nextLine();
                f = isUserExists(name, password);
                if (f) {
                    this.name = name;
                    this.password = password;
                    // запустили слушателя сообщений
                    new Thread(receiverMessagesTask).start();
                    clientConnect();
                } else System.err.println("НЕПРАВИЛЬНОЕ ИМЯ ПОЛЬЗОВАТЕЛЯ ИЛИ ПАРОЛЬ");
            }
            while (!f);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isUserExists(String name, String password) {
        out.println(name);
        out.println(password);
        try {
            String i = in.readLine();
            if (i.equals("true")) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void sendMessage(String message) {
        out.println(message);
    }

    public void clientConnect() {
        out.println(name + " connect to server");
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String response = in.readLine();
                    if (response != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
