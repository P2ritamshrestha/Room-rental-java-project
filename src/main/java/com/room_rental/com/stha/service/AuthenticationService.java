package com.room_rental.com.stha.service;


import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.User;
import jakarta.mail.MessagingException;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface AuthenticationService {
    void signUp(SignUpRequest signUpRequest) throws MessagingException;
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
