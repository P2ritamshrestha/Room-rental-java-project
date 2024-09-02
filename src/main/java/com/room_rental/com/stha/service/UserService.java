package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.UserRequestDTO;
import com.room_rental.com.stha.DTO.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

     UserDetailsService userDetailsService();

    List<UserResponseDTO> getUsers();

}
