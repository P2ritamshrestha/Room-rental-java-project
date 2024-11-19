package com.room_rental.com.stha.DTO;

import lombok.*;

/**
 * @author pritam shrestha
 * @version v1.0
 * @date 2024-10-22
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiImageDetails {


    private String imageFileUrl;
    private String imageFileName;
}
