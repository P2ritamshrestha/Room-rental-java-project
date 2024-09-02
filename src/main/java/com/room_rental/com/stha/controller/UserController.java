package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.UserRequestDTO;
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

    @GetMapping()
    public ResponseEntity<List<UserRequestDTO>> getUsers() {
        List<UserRequestDTO> userRequestDTO = userService.getUsers();
        return new ResponseEntity(userRequestDTO, HttpStatus.OK);
    }

}
