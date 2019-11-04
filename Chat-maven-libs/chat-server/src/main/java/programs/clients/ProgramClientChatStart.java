package die.mass.programs.clients;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import die.mass.clients.SocketClient;

import java.util.Scanner;

/**
 * 12.02.2019
 * ProgramClientChatStart
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Parameters(separators = "=")
public class ProgramClientChatStart {

    @Parameter(names = "--server-ip")
    private static String ip = "127.0.0.1";
    @Parameter(names = "--server-port")
    private static int port = 6667;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocketClient client = new SocketClient();
        client.startConnection(ip, port);
        while (true) {
            String message = scanner.nextLine();
            client.sendMessage(message);
        }
    }
}
