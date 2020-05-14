package project.services.users;

import project.dto.user.SignInDto;
import project.dto.user.TokenDto;

public interface SignInService {


	TokenDto signIn(SignInDto signInData);
}
