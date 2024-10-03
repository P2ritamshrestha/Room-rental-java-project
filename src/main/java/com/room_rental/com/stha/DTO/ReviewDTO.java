package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    @NotBlank
    @Min(0)
    @Max(160)
    private String message;
    private MultipartFile image;

    @NotBlank
    private Integer rating;
    private Date createdDate;
}
