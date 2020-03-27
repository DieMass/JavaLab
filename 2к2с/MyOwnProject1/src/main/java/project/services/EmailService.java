package project.services;

import project.dto.UserVerifiedDto;

public interface EmailService {

    void sendMessage(String email, UserVerifiedDto userDto);

    void sendImage(String email, String imagePath);
}
