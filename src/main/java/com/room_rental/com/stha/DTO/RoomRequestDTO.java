package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {

    @NotNull  // Ensures that purpose is not null
    private Purpose purpose;

    @NotNull  // Only applicable for String types
    @Size(min = 1, max = 100)
    private String title;

    @NotNull  // Ensures that category is not null
    private Category category;

    @NotNull  // Ensures that price is not null
    @Min(0)   // Price should not be negative
    private Integer price;

    private Date createdDate;

    @NotNull  // Ensures that negotiable is not null (since it's a Boolean)
    private Boolean negotiable;
    @NotNull
    private MultipartFile image;

    // Amenities:
    @NotNull
    private LocalDate dateOfBuild;

    @Min(0)
    private Integer bedRoom;

    private Boolean kitchen;
    private Boolean bathRoom;
    private Face face;
    private Boolean furnishing;
    private Boolean parking;
    private Boolean balcony;
    private Floor floor;
    private Boolean waterFacility;

    private RoodType roodType;

    // More Details:
    @NotBlank
    @Pattern(regexp = "^98\\d{8}$", message = "Phone number must start with 98 and be exactly 10 digits")
    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 200)
    private String location;

    @NotNull
    @Size(max = 100)
    private String description;


    private List<LocalArea> localArea;

    @Max(5)
    private List<MultipartFile> morePhotos;
}
