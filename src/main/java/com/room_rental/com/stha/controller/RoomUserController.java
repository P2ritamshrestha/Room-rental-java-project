package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.ProfileDTO;
import com.room_rental.com.stha.service.RoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomUserController {
    private final RoomUserService roomUserService;


    @PostMapping("/updateProfile")
    public String updateProfile(@RequestBody ProfileDTO profileDTO) {
        return profileDTO.toString();
    }

}
