package com.project.repository;

import com.project.dto.FurnitureSearchDTO;
import com.project.dto.MainFurnitureDTO;
import com.project.entity.FurnitureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyFurnitureRepository {

    Page<FurnitureEntity> getAdminFurniturePage(FurnitureSearchDTO furnitureSearchDTO, Pageable pageable);
//    Page<Furniture> getMainFurniturePage(FurnitureSearchDTO furnitureSearchDTO, Pageable pageable);
Page<MainFurnitureDTO> getMainFurniturePage(String findwhat, Pageable pageable);
}
