package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.ReviewDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final JwtService jwtService;

    @GetMapping()
    public String getMsg(){
        return "this is after login room page";
    }


}
