package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.MultiImageDetails;
import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.DTO.RoomResponseDTO;
import com.room_rental.com.stha.exception.RoomRentalException;
import com.room_rental.com.stha.models.MultiImages;
import com.room_rental.com.stha.models.Room;
import com.room_rental.com.stha.repository.MultiImageRepo;
import com.room_rental.com.stha.repository.RoomRepository;
import com.room_rental.com.stha.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

   private final RoomRepository roomRepository;
    private final MultiImageRepo multiImageRepo;

    @Value("${Room.image}")
    private String path;
    @Value("${Multiple.room.image}")
    private String path_multiImages;


    @Override
    public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) throws IOException {
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
                .Wishlist(false)
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

        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
        roomResponseDTO.setRoom(room);

        roomRepository.save(room);
            List<MultiImageDetails>  multiImages = new ArrayList<>();
        if(Objects.nonNull(roomRequestDTO.getMorePhotos())){
            for(MultipartFile image :roomRequestDTO.getMorePhotos()){
                MultiImageDetails multiImage = storeImage(image,room);
                multiImages.add(multiImage);
            }
        }
        roomResponseDTO.setMorePhotos(multiImages);
        return roomResponseDTO;
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
    public RoomResponseDTO getRoomById(String  roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RoomRentalException("Room not Found!"));

        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .room(room)
                .build();
        return roomResponseDTO;
    }

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDTO> roomResponseDTOS = rooms.stream()
                .map(room -> {
                    RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
                    roomResponseDTO.setRoom(room);
                    return roomResponseDTO;
                })
                .collect(Collectors.toList());
        return roomResponseDTOS;
    }

    @Override
    public void setTrueOrFalseWishlist(String roomId) {
        Room room= roomRepository.findById(roomId).orElseThrow(()->new RoomRentalException("Room not Found!"));

        if(!room.isWishlist()){
            room.setWishlist(true);
        }else {
            room.setWishlist(false);
        }
        roomRepository.save(room);
    }

    private MultiImageDetails storeImage(MultipartFile file,Room room) throws IOException {

        MultiImages multiImages = new MultiImages();

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File newFile = new File(path_multiImages);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        Path filePath = Paths.get(path_multiImages, uniqueFileName);

        Files.copy(file.getInputStream(), filePath);
        multiImages.setImageFileName(uniqueFileName);
        multiImages.setImageFileUrl(filePath.toString());
        multiImages.setRoom(room);
        multiImageRepo.save(multiImages);
        return MultiImageDetails.builder()
                .imageFileName(uniqueFileName)
                .imageFileUrl(filePath.toString())
                .build();

    }

    public Resource getImageAsResource(String imageName,String photo) throws IOException {

        if(Objects.nonNull(photo)){
            Path imagePath = Paths.get(path_multiImages, imageName);
            if (Files.exists(imagePath)) {
                Resource resource = new UrlResource(imagePath.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
                }
            } else {
                throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
            }
        }else {
            Path imagePath = Paths.get(path, imageName);
            if (Files.exists(imagePath)) {
                Resource resource = new UrlResource(imagePath.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
                }
            } else {
                throw new FileNotFoundException("Could not find the image " + imageName + " on the server.");
            }
        }
    }
}
