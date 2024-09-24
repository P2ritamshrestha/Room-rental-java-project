package com.room_rental.com.stha.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "room_details")
public class Room {
    @Id
    private String id;
    private Purpose purpose;
    private String title;
    private Category category;
    private Integer price;
    private Date createdDate;
    private Boolean negotiable;
    private String imageFileUrl;
    private String imageFileName;

    // Amenities:
    private LocalDate dateOfBuild;
    private Integer bedRoom;
    private Boolean kitchen;
    private Boolean bathRoom;
    private Face face;
    private Boolean parking;
    private Boolean balcony;
    private Floor floor;
    private Boolean waterFacility;

    // More Details:
    private String phoneNumber;
    private String location;
    private String description;
    private LocalArea localArea;
}