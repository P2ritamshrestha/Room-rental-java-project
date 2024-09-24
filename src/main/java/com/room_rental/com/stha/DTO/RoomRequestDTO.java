package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {
    @NotBlank
    private Purpose purpose;

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    private Category category;

    @NotBlank
    @Min(0)
    private Integer price;

    private Date createdDate;

    @NotBlank
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
    @NotBlank
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotBlank
    @Size(min = 1, max = 200)
    private String location;

    @Size(max = 1000)
    private String description;

    private LocalArea localArea;
}
