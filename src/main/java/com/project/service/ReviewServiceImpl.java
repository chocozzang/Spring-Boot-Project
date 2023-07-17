package com.project.service;

import com.project.entity.CartFurnitureEntity;
import com.project.entity.FurnitureEntity;
import com.project.entity.MemberEntity;
import com.project.entity.ReviewEntity;
import com.project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements  ReviewService{
    @Autowired
    CartFurnitureEntity cartFurnitureEntity;
    @Autowired
    FurnitureEntity furnitureEntity;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MemberEntity memberEntity;

    @Override
    public void reService(ReviewEntity reviewEntity) {
        reviewRepository.save(reviewEntity);
    }

    @Override
    public List<ReviewEntity> getReviews(Long fid) {
        return reviewRepository.getAllReviews(fid);
    }

//    @Override
//    public void createReview(Furniture furniture) {
//
//    }


//    @Override
//    public void createReview(Review review) {
//
//        reviewRepository.save(review);
//    }

//    @Override
//    public void addReview(Furniture furniture){
////        reviewRepository.save(furniture);
//
//    }

}
