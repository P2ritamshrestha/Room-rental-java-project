package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.AuthenticationService;
import com.room_rental.com.stha.service.JwtService;
import com.room_rental.com.stha.service.RoomUserService;
import com.room_rental.com.stha.service.impl.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final RoomUserService roomUserService;
    private final AuthenticationServiceImpl authenticationServiceImpl;


    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws IOException, MessagingException {

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Passwords do not match");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        authenticationService.signUp(signUpRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        String username = jwtService.extractUserName(token);
        if(Objects.nonNull(username)) {
            User user = roomUserService.getExtractDetails(username);
            user.setActive(true);
            roomUserService.saveConfirmUser(user);
            return ResponseEntity.ok("Registration confirmed! Your account is now active.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
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