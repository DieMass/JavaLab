package die.mass.services;

import die.mass.models.Message;
import die.mass.protocol.Payload;
import die.mass.protocol.Protocol;
import die.mass.repositories.MessageRepository;
import die.mass.servers.GetTokenServiceImpl;

import java.time.LocalDateTime;

public class MessageService {

    private Protocol protocolIn = new Protocol();
    private Protocol protocolOut = new Protocol();
    private MessageRepository messageRepository;
    private GetTokenServiceImpl getTokenService;

    public MessageService(MessageRepository messageRepository, GetTokenServiceImpl getTokenService) {
        this.messageRepository = messageRepository;
        this.getTokenService = getTokenService;
    }

    public Protocol createOut(LocalDateTime localDateTime, Protocol protocolIn) {
        String message = protocolIn.getPayload().getMessage();
        protocolOut.setPayload(new Payload());
        protocolOut.getPayload().setName(protocolIn.getPayload().getName());
        protocolOut.getPayload().setTime(localDateTime.toString());
        if (message == null) {
            protocolOut.setHeader("Login");
        } else {
            String name = getTokenService.getData(protocolIn.getPayload().getToken(), "name");
            messageRepository.save(new Message(null, message, name, localDateTime.toString()));
            protocolOut.setHeader("Message");
            protocolOut.getPayload().setMessage(protocolIn.getPayload().getMessage());
            protocolOut.getPayload().setName(name);
        }
        return protocolOut;
    }

}
