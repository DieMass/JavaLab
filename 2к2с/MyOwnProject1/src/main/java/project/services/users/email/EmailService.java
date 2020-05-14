package project.services.users.email;

import project.dto.user.UserVerifiedDto;

public interface EmailService {

    void sendMessage(String email, UserVerifiedDto userDto);

    void sendImage(String email, String imagePath);
}
