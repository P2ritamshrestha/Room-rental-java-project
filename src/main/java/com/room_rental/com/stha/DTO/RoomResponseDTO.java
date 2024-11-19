package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

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
@Slf4j
public class RoomResponseDTO {
    private Room room;
    private List<MultiImageDetails> morePhotos;
}
