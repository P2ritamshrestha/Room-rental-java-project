package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.exception.RoomRentalException;
import com.room_rental.com.stha.models.Role;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.AuthenticationService;
import com.room_rental.com.stha.service.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;


    @Value("${Profile.image}")
    private String path;


    public void signUp(SignUpRequest signUpRequest) throws MessagingException {
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
        var jwt = jwtService.generateToken(user.getUsername());
        emailService.sendConfirmLinkToEmail(signUpRequest.getEmail(), jwt);

    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );

            // Find the user in the database
            var user = userRepository.findByUsername(signInRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Check if the user is active
            if (user.isActive()) {
                // Generate JWT and refresh token
                var jwt = jwtService.generateToken(user.getUsername());
                var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

                // Create response with tokens
                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
                jwtAuthenticationResponse.setToken(jwt);
                jwtAuthenticationResponse.setRefreshToken((String)refreshToken);
                return jwtAuthenticationResponse;
            } else {
                // If the user is not verified
                throw new RoomRentalException("User not verified");
            }
        } catch (AuthenticationException ex) {
            // Handle invalid username or password
            throw new RoomRentalException("Invalid username or password");
        }
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username= jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(jwtService.isValidToken(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user.getUsername());
            JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }



    public void processOAuthPostLogin(OAuth2AuthenticationToken authenticationToken) throws IOException {
        OAuth2User oauth2User = authenticationToken.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String pictureUrl = oauth2User.getAttribute("picture");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFullName(name);
                    newUser.setActive(true);
                    newUser.setUsername(email);
                    newUser.setRole(Role.USER);
                    return newUser;
                });

        if (pictureUrl != null) {
            // Generate a unique file name
            String uniqueFileName = UUID.randomUUID().toString() + "_profile.jpg";
            Path uploadPath = Paths.get(path);
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Create directories if they don't exist
            Files.createDirectories(uploadPath);

            // Download and save the image
            try (InputStream in = new URL(pictureUrl).openStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            user.setImageName(uniqueFileName);
            user.setImagePath(filePath.toString());
        }

        userRepository.save(user);
    }


}
