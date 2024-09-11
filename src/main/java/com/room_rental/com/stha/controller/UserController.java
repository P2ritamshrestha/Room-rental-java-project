package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.UserResponseDTO;
import com.room_rental.com.stha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> userResponseDTO = userService.getUsers();
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
