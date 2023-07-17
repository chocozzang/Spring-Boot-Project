package com.project.dto;

import com.project.entity.FurnitureEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FurnitureDTO {

    private Long fid;
    private String furnitureName;
    private String furnitureType;
    private Long furniturePrice;
    private Long furnitureStock;
    private Integer furnitureWidth;
    private Integer furnitureLength;
    private Integer furnitureHeight;

    private List<FurnitureImgDTO> furnitureImgDTOList = new ArrayList<>();

    // 이미지 수정 시 이미지의 아이디 저장
    private List<Long> furnitureImgIds = new ArrayList<>();

    public FurnitureEntity createFurniture() {
        return modelMapper.map(this, FurnitureEntity.class);
    }

    public FurnitureEntity toEntity() {
        FurnitureEntity furnitureEntity = FurnitureEntity.builder()
                .fid(fid)
                .furnitureName(furnitureName)
                .furnitureType(furnitureType)
                .furniturePrice(furniturePrice)
                .furnitureStock(furnitureStock)
                .furnitureWidth(furnitureWidth)
                .furnitureLength(furnitureLength)
                .furnitureHeight(furnitureHeight)
                .build();
        return furnitureEntity;
    }

    @Builder
    public FurnitureDTO(Long fid, String furnitureName, String furnitureType, Long furniturePrice, Long furnitureStock,
                        int furnitureWidth, int furnitureLength, int furnitureHeight) {
        this.fid = fid;
        this.furnitureName = furnitureName;
        this.furnitureType = furnitureType;
        this.furniturePrice = furniturePrice;
        this.furnitureStock = furnitureStock;
        this.furnitureWidth = furnitureWidth;
        this.furnitureLength = furnitureLength;
        this.furnitureHeight = furnitureHeight;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static FurnitureDTO of(FurnitureEntity furnitureEntity) {
        return modelMapper.map(furnitureEntity, FurnitureDTO.class);
    }
    public static FurnitureDTO toDTO(FurnitureFormDTO furnitureFormDTO) {return modelMapper.map(furnitureFormDTO, FurnitureDTO.class);}
    public static FurnitureFormDTO toForm(FurnitureDTO furnitureDTO) {return modelMapper.map(furnitureDTO, FurnitureFormDTO.class);}

}
