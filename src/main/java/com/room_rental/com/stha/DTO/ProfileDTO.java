package com.room_rental.com.stha.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private MultipartFile image;
    private  String username;
    private String email;
    private String phone;
    private String address;
}
