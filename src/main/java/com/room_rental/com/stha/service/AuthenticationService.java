package com.room_rental.com.stha.service;


import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.User;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
