package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.DTO.RoomResponseDTO;
import com.room_rental.com.stha.models.Room;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface RoomService {
    RoomResponseDTO createRoom(RoomRequestDTO room) throws IOException;
    void deleteRoom(String roomId);
    Room updateRoom(RoomRequestDTO roomRequestDTO);
    RoomResponseDTO getRoomById(String roomId);
    List<RoomResponseDTO> getAllRooms();

    void setTrueOrFalseWishlist(String roomId);

    Resource getImageAsResource(String imageName,String photo)throws IOException;
}
