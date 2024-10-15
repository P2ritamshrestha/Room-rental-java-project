package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                return userRepository.findByUsernameOrEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}