package com.project.repository;

import com.project.entity.FurnitureImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FurnitureImageRepository extends JpaRepository<FurnitureImageEntity, Long> {

    @Query(value = "select * from furniture_image fi where fi.furniture_id = :fid order by fi.furniture_image_id asc", nativeQuery = true)
    List<FurnitureImageEntity> findByFidOrderByFiidAsc(Long fid);

    @Query(value = "select * from furniture_image fi where fi.furniture_id = :fid", nativeQuery = true)
    List<FurnitureImageEntity> findByFurnitureId(@Param("fid") Long fid);
}
