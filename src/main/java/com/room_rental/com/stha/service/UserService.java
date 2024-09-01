package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.UserRequestDTO;

import java.util.List;

public interface UserService {

    void registerUser(UserRequestDTO userRequestDTO);

    List<UserRequestDTO> getUsers();

}
