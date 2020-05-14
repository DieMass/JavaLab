package project.services.users.email;

import project.dto.user.UserVerifiedDto;

import javax.mail.internet.MimeMessage;

public interface MessageContentService {

    String getHelloPageText(UserVerifiedDto userVerifiedDto, MimeMessage message);

    String getImagePage(String email, String imagePath, MimeMessage message);
}
