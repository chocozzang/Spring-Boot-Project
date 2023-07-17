package com.project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name="furniture_image")
@Getter
@Setter
@ToString
@Repository
public class FurnitureImageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="furniture_image_id")
    private Long fiid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "furniture_id")
    private FurnitureEntity furnitureEntity;

    @Column(name = "img_filename")
    private String imgFilename; // 이미지 파일명

    @Column(name = "origin_filename")
    private String oriImgName; // 원본 이미지 파일명

    @Column(nullable = false, name = "img_url")
    private String imgUrl; // 이미지 조회 경로

    private String repimgYn; // 대표 이미지 여부

    public void updateFurnitureImg(String oriImgName, String imgFilename, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgFilename = imgFilename;
        this.imgUrl = imgUrl;
    }
}
