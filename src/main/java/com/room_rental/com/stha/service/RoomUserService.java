package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.ChangePasswordDTO;
import com.room_rental.com.stha.DTO.ProfileDTO;
import com.room_rental.com.stha.models.User;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface RoomUserService {

    void changePassword(ChangePasswordDTO changePasswordDTO, String id);
    Resource getImageAsResource(String imageName) throws IOException;
    User updateProfile(String id, ProfileDTO profileDTO) throws IOException;

    User getExtractDetails(String username);
}
