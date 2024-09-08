package com.room_rental.com.stha.service;


import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.User;

import java.io.IOException;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest) throws IOException;
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    void changePassword(ChangePasswordDTO changePasswordDTO, String id);
}
