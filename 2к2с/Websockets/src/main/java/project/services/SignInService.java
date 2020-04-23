package project.services;

import project.models.SignInDto;
import project.models.TokenDto;

public interface SignInService {

	TokenDto signIn(SignInDto signInData);
}
