package com.project.service;

import com.project.entity.ReviewEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    void reService(ReviewEntity reviewEntity);
//    public void createReview(Furniture furniture);

    List<ReviewEntity> getReviews(Long fid);
}
