package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

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

    private MultipartFile image;

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
    @NotNull
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 200)
    private String location;

    @NotNull
    @Size(max = 1000)
    private String description;

    private LocalArea localArea;
}
