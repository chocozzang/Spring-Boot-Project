package com.project.dto;

import com.project.entity.FurnitureEntity;
import com.project.entity.FurnitureImageEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class FurnitureImgDTO {

    private Long fiid;
    private FurnitureEntity furnitureEntity;
    private String imgFilename; // 이미지 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repimgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static FurnitureImgDTO of(FurnitureImageEntity furnitureImageEntity) {
        return modelMapper.map(furnitureImageEntity, FurnitureImgDTO.class);
    }
}
