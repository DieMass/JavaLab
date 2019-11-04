package die.mass.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import die.mass.protocol.*;


public class SocketClient {
    // поле, содержащее сокет-клиента
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    private String password;
    private Protocol protocol = new Protocol();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Scanner scanner;

    // начало сессии - получаем ip-сервера и его порт
    public void startConnection(String ip, int port) {
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // создаем подключение
            clientSocket = new Socket(ip, port);
            // получили выходной поток
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // входной поток
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean f = false;
            scanner = new Scanner(System.in);
            protocol.setPayload(new Payload());
            System.out.println("Зарегистрироваться (0) или войти (1) ?");
            String a = scanner.nextLine();
            while (!a.equals("1") && !a.equals("0")) {
                System.err.println("I DONT UNDERSTAND");
            }
            do {
                System.out.print("Print name: ");
                protocol.getPayload().setName(scanner.nextLine());
                System.out.print("Print password: ");
                protocol.getPayload().setPassword(scanner.nextLine());
                if (a.equals("0")) {
                    protocol.setHeader("SignUp");
                    System.out.println("now prot is " + getValueAsString(protocol));
                    f = isUserExists(protocol);
                    if(!f) {
                        System.err.println("USER ALREADY EXISTS");
                    } else {
                        protocol.setHeader("Login");
                        System.out.println("now prot is " + getValueAsString(protocol));
                        out.println(getValueAsString(protocol));
                    }
                } else {
                    protocol.setHeader("Login");
                    System.out.println("now prot is " + getValueAsString(protocol));
                    out.println(getValueAsString(protocol));
                    f = isUserExists(protocol);
                    if(!f) System.err.println("НЕПРАВИЛЬНОЕ ИМЯ ПОЛЬЗОВАТЕЛЯ ИЛИ ПАРОЛЬ");
                }
            } while (!f);
                this.name = protocol.getPayload().getName();
                this.password = protocol.getPayload().getPassword();
                // запустили слушателя сообщений
                new Thread(receiverMessagesTask).start();
//                clientConnect();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isUserExists(Protocol protocol) {
        out.println(getValueAsString(protocol));
        try {
            String s = in.readLine();
            System.out.println("isUserExits " + s);
            Protocol p = getValue(s);
            return p.getPayload().getCorrect();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getValueAsString(Protocol protocol) {
        try {
            return objectMapper.writeValueAsString(protocol);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private Protocol getValue(String string) {
        try {
            return objectMapper.readValue(string, Protocol.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }


    public void sendMessage(String text) {
        protocol.setPayload(new Payload());
        if(text.equals("getHistory")) {
            protocol.setHeader("Command");
            protocol.getPayload().setSize(scanner.nextInt());
            protocol.getPayload().setPage(scanner.nextInt());
        } else {
            protocol.setHeader("Message");
            Message message = new Message(text);
            protocol.setPayload(message);
        }
        out.println(getValueAsString(protocol));
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String response = in.readLine();
                    if (response != null) {
                        System.out.println(response);
                        Protocol p = objectMapper.readValue(response, Protocol.class);
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
