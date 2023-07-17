package com.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.support.ManagedArray;


@Getter @Setter
public class MainFurnitureDTO {

    private Long fid;
    private String furnitureName;
    private String furnitureType;
    private Long furniturePrice;
    private Long furnitureStock;
    private int furnitureWidth;
    private int furnitureLength;
    private int furnitureHeight;
    private String fileUrl;
    private int hits;

    @QueryProjection
    public MainFurnitureDTO(Long fid, String furnitureName, String furnitureType, Long furnitureStock, Long furniturePrice,
                            int furnitureWidth, int furnitureLength, int furnitureHeight, String fileUrl, int hits) {
        this.fid = fid;
        this.furnitureName = furnitureName;
        this.furnitureType = furnitureType;
        this.furnitureStock = furnitureStock;
        this.furniturePrice = furniturePrice;
        this.furnitureHeight = furnitureHeight;
        this.furnitureLength = furnitureLength;
        this.furnitureWidth = furnitureWidth;
        this.fileUrl = fileUrl;
        this.hits = hits;
    }
}
