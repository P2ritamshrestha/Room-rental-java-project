package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.models.Room;

import java.util.List;

public interface RoomService {
    void createRoom(RoomRequestDTO room);
    void deleteRoom(RoomRequestDTO room);
    void updateRoom(RoomRequestDTO roomRequestDTO);
    Room getRoom(int id);
    List<Room> getAllRooms();
}
