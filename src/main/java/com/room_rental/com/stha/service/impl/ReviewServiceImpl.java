package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.DTO.ReviewDTO;
import com.room_rental.com.stha.exception.RoomRentalException;
import com.room_rental.com.stha.models.Review;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.repository.ReviewRepository;
import com.room_rental.com.stha.repository.UserRepository;
import com.room_rental.com.stha.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Value("${Review.image}")
    private String path;

    @Override
    public void addReview(String username,ReviewDTO reviewDTO) throws IOException {
        User user = userRepository.findByUsername(username).get();
        Review review = Review.builder()
                .message(reviewDTO.getMessage())
                .rating(reviewDTO.getRating())
                .createdDate(new Date(System.currentTimeMillis()))
                .user(user)
                .build();

        if(Objects.nonNull(reviewDTO.getImage())){
            MultipartFile file = reviewDTO.getImage();

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File newFile = new File(path);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            Path filePath = Paths.get(path, uniqueFileName);

            Files.copy(file.getInputStream(), filePath);

            review.setImageName(uniqueFileName);
            review.setImagePath(filePath.toString());
        }
        reviewRepository.save(review);
    }

    @Override
    public ReviewDTO getReviewById(String username,String id) {
        Review review= reviewRepository.findById(id).orElseThrow(()->new RoomRentalException("Review not found"));
        if(Objects.nonNull(review) && review.getUser().getUsername().equals(username)) {
            ReviewDTO reviewDTO = ReviewDTO.builder()
                    .message(review.getMessage())
                    .rating(review.getRating())
                    .createdDate(review.getCreatedDate())
                    .build();
            return reviewDTO;
        }
        return null;
    }

    @Override
    public void deleteReviewById(String id) {
        reviewRepository.deleteById(id);
    }
}
