package project.services;

import project.dto.SignInDto;
import project.dto.TokenDto;

public interface SignInService {


	TokenDto signIn(SignInDto signInData);
}
