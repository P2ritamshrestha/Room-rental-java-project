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
import org.springframework.http.ResponseEntity;
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
import java.util.Map;
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

    String img = "";
    public User signUp(SignUpRequest signUpRequest) throws IOException {

//        if(Objects.isNull(signUpRequest.getImage())){
//            signUpRequest.setImage(getImageAsResource(img));
//        }
//
//
//        MultipartFile file = signUpRequest.getImage();
//
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//        File newFile = new File(path);
//        if(!newFile.exists()){
//            newFile.mkdirs();
//        }
//        Path filePath = Paths.get(path, uniqueFileName);
//
//        Files.copy(file.getInputStream(), filePath);


        User user = User.builder()
            .fullName(signUpRequest.getFullName())
            .email(signUpRequest.getEmail())
            .username(signUpRequest.getUsername())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .phoneNumber(signUpRequest.getPhoneNumber())
            .address(signUpRequest.getAddress())
//            .imageName(uniqueFileName)
//            .imagePath(filePath.toString())
            .role(Role.USER)
            .build();
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

}
