package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.ProfileDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.RoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class RoomUserServiceImpl implements RoomUserService {

    @Value("${Profile.image}")
    private String path;

    private final UserRepository userRepository;

    public String updateProfile(String id, ProfileDTO profileDTO) {
        User user= userRepository.findById(id).orElseThrow(RuntimeException::new);
        if(Objects.nonNull(profileDTO.getFullName())) {
            user.setFullName(profileDTO.getFullName());
        }
        if(Objects.nonNull(profileDTO.getEmail())) {
            user.setEmail(profileDTO.getEmail());
        }
        if(Objects.nonNull(profileDTO.getAddress())) {
            user.setAddress((profileDTO.getAddress()));
        }
        userRepository.save(user);
        return "Profile updated successfully";
    }

}
