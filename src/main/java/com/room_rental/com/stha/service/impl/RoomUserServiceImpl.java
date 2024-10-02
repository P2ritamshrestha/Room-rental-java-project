package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.ChangePasswordDTO;
import com.room_rental.com.stha.DTO.ProfileDTO;
import com.room_rental.com.stha.exception.PasswordMismatchException;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.JwtService;
import com.room_rental.com.stha.service.RoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.util.Objects;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class RoomUserServiceImpl implements RoomUserService {

    @Value("${Profile.image}")
    private String path;

    private final UserRepository userRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User updateProfile(String id, ProfileDTO profileDTO) throws IOException {
        User user= userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        user.setEmail(profileDTO.getEmail());
        user.setAddress(profileDTO.getAddress());
        user.setPhoneNumber(profileDTO.getPhone());

        if(Objects.nonNull(profileDTO.getImage())){
            MultipartFile file = profileDTO.getImage();

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File newFile = new File(path);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            Path filePath = Paths.get(path, uniqueFileName);

            Files.copy(file.getInputStream(), filePath);

            user.setImageName(uniqueFileName);
            user.setImagePath(filePath.toString());
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User getExtractDetails(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Override
    public void saveConfirmUser(User user) {
        userRepository.save(user);
    }


    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, String id) {

        User user= userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())){

            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                throw new PasswordMismatchException("New password and confirm password do not match");
            }
            if(Objects.equals(changePasswordDTO.getCurrentPassword(), changePasswordDTO.getNewPassword())){
                throw new PasswordMismatchException("Current password and New password should not be the same");
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
