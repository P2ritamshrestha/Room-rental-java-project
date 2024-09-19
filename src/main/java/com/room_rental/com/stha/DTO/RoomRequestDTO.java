package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {

    private Purpose purpose;
    private String title;
    private Category category;
    private Integer price;
    private Boolean negotiable;
    private MultipartFile imageFile;


    // Amenities:
    private LocalDate dateOfBuild;
    private Integer bedRoom;
    private boolean kitchen;
    private boolean bathRoom;
    private Face face;
    private boolean parking;
    private boolean balcony;
    private Floor floor;
    private boolean waterFacility;

    // More Details:
    private String phoneNumber;
    private String location;
    private String description;
    private LocalAreal localAreal;

//    private MultipartFile video;
}
