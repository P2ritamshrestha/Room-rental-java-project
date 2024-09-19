package com.room_rental.com.stha.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
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

    @NotNull
    private Purpose purpose;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    private Category category;

    @NotNull
    @Min(0)
    private Integer price;

    private Date createdDate;

    private Boolean negotiable;

    private String imageFileUrl;
    private String imageFileName;

    // Amenities:
    private LocalDate dateOfBuild;

    @Min(0)
    private Integer bedRoom;

    private Boolean kitchen;
    private Boolean bathRoom;
    private Face face;
    private Boolean parking;
    private Boolean balcony;
    private Floor floor;
    private Boolean waterFacility;

    // More Details:
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 200)
    private String location;

    @Size(max = 1000)
    private String description;

    private LocalAreal localAreal;

    private String videoUrl;
}