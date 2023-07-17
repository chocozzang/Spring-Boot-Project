package com.project.repository;


import com.project.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    ReviewEntity findByFid(Long fid);

    @Query(value="select * from review r where r.furniture_id = :fid", nativeQuery = true)
    List<ReviewEntity> getAllReviews(Long fid);
}
