package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.UserRequestDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // user details:
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<UserRequestDTO> getUsers() {
        List<User> users = userRepository.findAll();
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        return users.stream().map(user -> {
            userRequestDTO.setFullName(user.getFullName());
            userRequestDTO.setEmail(user.getEmail());
            userRequestDTO.setPhoneNumber(user.getPhoneNumber());
            return userRequestDTO;
        }).toList();
    }
}