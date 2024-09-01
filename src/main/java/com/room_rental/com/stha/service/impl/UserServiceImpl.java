package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.UserRequestDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.UserService;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public void registerUser(UserRequestDTO userRequestDTO) {
        User user = User.builder()
                .fullName(userRequestDTO.getFullName())
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .address(userRequestDTO.getAddress())
                .password(userRequestDTO.getPassword())
                .conformPassword(userRequestDTO.getConformPassword())
                .build();
        userRepository.save(user);
    }

    @Override
    public List<UserRequestDTO> getUsers() {
        List<User> users = userRepository.findAll();
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        List<UserRequestDTO> userRequestDTOList = users.stream().map(user -> {
            userRequestDTO.setFullName(user.getFullName());
            userRequestDTO.setEmail(user.getEmail());
            userRequestDTO.setPhoneNumber(user.getPhoneNumber());
            return userRequestDTO;
        }).toList();
        return userRequestDTOList;
    }
}