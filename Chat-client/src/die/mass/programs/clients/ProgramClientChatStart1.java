//package die.mass.programs.clients;
//
//import die.mass.clients.SocketClient;
//
//import java.util.Scanner;
//
///**
// * 12.02.2019
// * ProgramClientChatStart
// *
// * @author Sidikov Marsel (First Software Engineering Platform)
// * @version v1.0
// */
//public class ProgramClientChatStart1 {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Print name: ");
//        String name = scanner.nextLine();
//        System.out.print("Print password: ");
//        String password = scanner.nextLine();
//        SocketClient client = new SocketClient();
//        client.startConnection("127.0.0.1", 6666);
//        while (true) {
//            String message = scanner.nextLine();
//            client.sendMessage(message);
//        }
//    }
//}
