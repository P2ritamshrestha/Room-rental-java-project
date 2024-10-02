package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.exception.RoomRentalException;
import com.room_rental.com.stha.models.Room;
import com.room_rental.com.stha.repository.RoomRepository;
import com.room_rental.com.stha.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

   private final RoomRepository roomRepository;

    @Value("${Room.image}")
    private String path;


    @Override
    public Room createRoom(RoomRequestDTO roomRequestDTO) throws IOException {
        Room room = Room.builder()
                .purpose(roomRequestDTO.getPurpose())
                .title(roomRequestDTO.getTitle())
                .description(roomRequestDTO.getDescription())
                .createdDate(new Date(System.currentTimeMillis()))
                .category(roomRequestDTO.getCategory())
                .price(roomRequestDTO.getPrice())
                .negotiable(roomRequestDTO.getNegotiable())
                .dateOfBuild(roomRequestDTO.getDateOfBuild())
                .bedRoom(roomRequestDTO.getBedRoom())
                .furnishing(roomRequestDTO.getFurnishing())
                .kitchen(roomRequestDTO.getKitchen())
                .bathRoom(roomRequestDTO.getBathRoom())
                .face(roomRequestDTO.getFace())
                .parking(roomRequestDTO.getParking())
                .balcony(roomRequestDTO.getBalcony())
                .floor(roomRequestDTO.getFloor())
                .roodType(roomRequestDTO.getRoodType())
                .waterFacility(roomRequestDTO.getWaterFacility())
                .phoneNumber(roomRequestDTO.getPhoneNumber())
                .location(roomRequestDTO.getLocation())
                .localArea(roomRequestDTO.getLocalArea())
                .build();
        if(Objects.nonNull(roomRequestDTO.getImage())){
            MultipartFile file = roomRequestDTO.getImage();

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File newFile = new File(path);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            Path filePath = Paths.get(path, uniqueFileName);

            Files.copy(file.getInputStream(), filePath);

            room.setImageFileName(uniqueFileName);
            room.setImageFileUrl(filePath.toString());
        }
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);

    }

    @Override
    public Room updateRoom(RoomRequestDTO roomRequestDTO) {
        return null;
    }

    @Override
    public Room getRoom(String  roomId) {
        return roomRepository.findById(roomId).orElseThrow(()->new RoomRentalException("Room not Found!"));
    }

    @Override
    public List<Room> getAllRooms() {
        return List.of();
    }
}
