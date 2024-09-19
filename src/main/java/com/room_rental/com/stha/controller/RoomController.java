package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveRoom(@ModelAttribute @Valid RoomRequestDTO roomRequestDTO) throws IOException {
        return ResponseEntity.ok(roomService.createRoom(roomRequestDTO));
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable String roomId){
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteRoomById(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Deleted room");
    }

}
