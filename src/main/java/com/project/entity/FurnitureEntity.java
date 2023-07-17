package com.project.entity;

import com.project.dto.FurnitureDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "furniture")
@Repository
@NoArgsConstructor
public class FurnitureEntity {

    @Id @Column(name = "furniture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    @Column(nullable = false, name = "furniture_name")
    private String furnitureName;

    @Column(nullable = false, name = "furniture_type")
    private String furnitureType;

    @Column(nullable = false, name = "furniture_price")
    private Long furniturePrice;

    @Column(nullable = false, name = "furniture_stock")
    private Long furnitureStock;

    @Column(nullable = false, name = "furniture_width")
    private int furnitureWidth;

    @Column(nullable = false, name = "furniture_length")
    private int furnitureLength;

    @Column(nullable = false, name = "furniture_height")
    private int furnitureHeight;

    private String fileUrl;

    @ColumnDefault("0")
    @Column(name = "hits")
    private int hits;

    @OneToMany(mappedBy = "furnitureEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FurnitureImageEntity> imgs = new ArrayList<>();

    @Builder
    public FurnitureEntity(Long fid, String furnitureName, String furnitureType, Long furniturePrice, Long furnitureStock,
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

    public void updateFurniture(FurnitureDTO furnitureDTO) {
        this.fid = furnitureDTO.getFid();
        this.furnitureName = furnitureDTO.getFurnitureName();
        this.furnitureType = furnitureDTO.getFurnitureType();
        this.furniturePrice = furnitureDTO.getFurniturePrice();
        this.furnitureStock = furnitureDTO.getFurnitureStock();
        this.furnitureWidth = furnitureDTO.getFurnitureWidth();
        this.furnitureLength = furnitureDTO.getFurnitureLength();
        this.furnitureHeight = furnitureDTO.getFurnitureHeight();
    }

}
