package com.room_rental.com.stha.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author pritam shrestha
 * @version v1.0
 * @date 2024-11-10
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "multi_images")
public class MultiImages {
    @Id
    private String id;

    private String imageFileUrl;
    private String imageFileName;

    @DBRef
    private Room room;
}
