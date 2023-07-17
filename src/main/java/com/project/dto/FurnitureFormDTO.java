package com.project.dto;

import com.project.entity.FurnitureEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FurnitureFormDTO {

    private Long fid;

    @NotBlank(message = "가구명은 필수 입력 값입니다.")
    private String furnitureName;

    @NotBlank(message = "가구 타입은 필수 선택 값입니다.")
    private String furnitureType;

    @NotNull(message = "가구 가격은 필수 입력 값입니다.")
    private Long furniturePrice;

    @NotNull(message = "가구 재고 수량은 필수 입력 값입니다.")
    private Long furnitureStock;

    @NotNull(message = "가구 가로 길이는 필수 입력 값입니다.")
    private Integer furnitureWidth;

    @NotNull(message = "가구 세로 길이는 필수 입력 값입니다.")
    private Integer furnitureLength;

    @NotNull(message = "가구 높이는 필수 입력 값입니다.")
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
    public FurnitureFormDTO(Long fid, String furnitureName, String furnitureType, Long furniturePrice, Long furnitureStock,
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

    public static FurnitureFormDTO of(FurnitureEntity furnitureEntity) {
        return modelMapper.map(furnitureEntity, FurnitureFormDTO.class);
    }

}
