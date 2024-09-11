package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.models.Room;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/room")
public class RoomController {

//
//    private Room saveRoom(RoomRequestDTO roomRequestDTO) {
//        Room room= Room.builder().build();
//        return room;
//    }

    @GetMapping()
    public String getMsg(){
        return "this is after login room page";
    }
}
