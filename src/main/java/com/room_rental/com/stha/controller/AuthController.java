package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.JwtAuthenticationResponse;
import com.room_rental.com.stha.DTO.RefreshTokenRequest;
import com.room_rental.com.stha.DTO.SignInRequest;
import com.room_rental.com.stha.DTO.SignUpRequest;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest sign) {
        return ResponseEntity.ok(authenticationService.signUp(sign));
    }
    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}