package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws IOException {

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Passwords do not match");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        User newUser = authenticationService.signUp(signUpRequest);
        return ResponseEntity.ok(newUser);
    }


    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable String id){
        authenticationService.changePassword(changePasswordDTO,id);
        return ResponseEntity.ok("Password changed successfully");
    }

}