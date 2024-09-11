package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.models.Role;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.AuthenticationService;
import com.room_rental.com.stha.service.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    public User signUp(SignUpRequest signUpRequest) throws MessagingException {
        User user = User.builder()
            .fullName(signUpRequest.getFullName())
            .email(signUpRequest.getEmail())
            .username(signUpRequest.getUsername())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .phoneNumber(signUpRequest.getPhoneNumber())
            .address(signUpRequest.getAddress())
            .isActive(false)
            .role(Role.USER)
            .build();
            user.setImageName("09b18213-73be-4fc0-8b93-d9ddf5eda753_default.jpg");
            user.setImagePath("image\\profilePicture\\09b18213-73be-4fc0-8b93-d9ddf5eda753_default.jpg");
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        emailService.sendConfirmLinkToEmail(signUpRequest.getEmail(), jwt);
        return user;
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if((user.isActive())) {
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken((String) refreshToken);
            return jwtAuthenticationResponse;
        }else {
            throw new UsernameNotFoundException("User not verified");
        }
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username= jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(jwtService.isValidToken(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }



    public User processOAuthPostLogin(OAuth2AuthenticationToken authenticationToken) {
        OAuth2User oauth2User = authenticationToken.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            // Create a new user if they don't exist
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullName(name);
            newUser.setRole(Role.USER); // Default role for OAuth users

            userRepository.save(newUser);  // Save the new user
            return newUser;
        } else {
            // Return existing user
            return existingUser.get();
        }
    }


}
