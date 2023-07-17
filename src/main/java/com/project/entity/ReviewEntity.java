package com.project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
@Repository
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="review_id")
    private Long rid;



    @Column(nullable = false, name = "review_content")
    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "furniture_id")
    private FurnitureEntity fid;



    public static ReviewEntity createReview(FurnitureEntity fid, Long rid, String reviewContent){
        ReviewEntity reviewEntity =new ReviewEntity();
        reviewEntity.rid=rid;
        reviewEntity.reviewContent=reviewContent;
        reviewEntity.fid=fid;
        return reviewEntity;

    }




}
