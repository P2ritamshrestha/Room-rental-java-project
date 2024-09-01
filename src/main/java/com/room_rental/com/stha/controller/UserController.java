package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.UserRequestDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.registerUser(userRequestDTO);
        Map<String,String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Map<String, List<UserRequestDTO>>> getUsers() {
        List<UserRequestDTO> userRequestDTO = userService.getUsers();
        Map<String,String> response = new HashMap<>();
        response.put("output", userRequestDTO.toString());
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
