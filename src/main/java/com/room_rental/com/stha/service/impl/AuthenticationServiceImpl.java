package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.*;
import com.room_rental.com.stha.exception.PasswordMismatchException;
import com.room_rental.com.stha.models.Role;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.AuthenticationService;
import com.room_rental.com.stha.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${Profile.image}")
    private String path;


    public User signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
            .fullName(signUpRequest.getFullName())
            .email(signUpRequest.getEmail())
            .username(signUpRequest.getUsername())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .phoneNumber(signUpRequest.getPhoneNumber())
            .address(signUpRequest.getAddress())
            .role(Role.USER)
            .build();
            user.setImageName("09b18213-73be-4fc0-8b93-d9ddf5eda753_default.jpg");
            user.setImagePath("image\\profilePicture\\09b18213-73be-4fc0-8b93-d9ddf5eda753_default.jpg");
        userRepository.save(user);
        return user;
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken((String) refreshToken);
        return jwtAuthenticationResponse;
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

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, String id) {

        User user= userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())){

            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                throw new PasswordMismatchException("New password and confirm password do not match");
            }
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        }else
            throw new PasswordMismatchException("current password do not match");
    }

    public Resource getImageAsResource(String imageName) throws IOException {
        Path imagePath = Paths.get(path, imageName);
        if (Files.exists(imagePath)) {
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
            }
        } else {
            throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
        }
    }



}
