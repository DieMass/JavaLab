package die.mass.servers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import die.mass.protocol.Message;
import die.mass.protocol.Protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class MessageResolver {

    private Protocol protocolIn;
    private Protocol protocolOut;
    private ObjectMapper objectMapper;
    private ServerContext serverContext;

    public MessageResolver(ServerContext serverContext) {
        this.protocolIn = serverContext.getProtocolIn();
        this.protocolOut = serverContext.getProtocolOut();
        this.objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.serverContext = serverContext;
    }

    public String successfullyEntering(String inputLine, PrintWriter out) {
        try {
            System.out.println(inputLine);
            protocolIn = objectMapper.readValue(inputLine, Protocol.class);
            return serverContext.getLoginService().parse(protocolIn, out);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String listen(String inputLine) {
        try {
            protocolIn = objectMapper.readValue(inputLine, Protocol.class);
            String header = protocolIn.getHeader();
            LocalDateTime localDateTime = LocalDateTime.now();
            switch (header) {
                case "LogOut":
                    // бегаем по всем клиентам и обовещаем их о событии
                    serverContext.getResponseService().responseLogout(protocolIn, protocolOut, localDateTime);
                    return "delete";
                case "Command":
                    serverContext.getResponseService().answering(serverContext.getCommandService().createOut(protocolIn),
                            serverContext.getGetTokenService().getData(protocolIn.getPayload().getToken(), "name"));
                    break;
                default:
                    System.out.println(inputLine);
                    protocolOut = serverContext.getMessageService().createOut(localDateTime, protocolIn);
                    serverContext.getResponseService().responseMessage(protocolOut);
                    break;
            }
            return "";
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
