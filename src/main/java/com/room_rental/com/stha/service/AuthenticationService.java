package com.room_rental.com.stha.service;


import com.room_rental.com.stha.DTO.JwtAuthenticationResponse;
import com.room_rental.com.stha.DTO.RefreshTokenRequest;
import com.room_rental.com.stha.DTO.SignInRequest;
import com.room_rental.com.stha.DTO.SignUpRequest;
import com.room_rental.com.stha.models.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
