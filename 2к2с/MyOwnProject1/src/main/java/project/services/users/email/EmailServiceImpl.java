package project.services.users.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import project.dto.user.UserVerifiedDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private MessageContentService messageContentService;
    @Autowired
    private ExecutorService executorService;

    @Override
    public void sendMessage(String email, UserVerifiedDto userDto) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = messageContentService.getHelloPageText(userDto, message);
            helper.setText(text, true);
            helper.addTo(email);
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        executorService.execute(() -> emailSender.send(message));
    }

    @Override
    public void sendImage(String email, String imagePath) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = messageContentService.getImagePage(email, imagePath, message);
            helper.setText(text, true);
            helper.addTo(email);
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        executorService.execute(() -> emailSender.send(message));
    }
}
