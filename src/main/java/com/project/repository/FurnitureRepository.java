package com.project.repository;

import com.project.entity.FurnitureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FurnitureRepository extends JpaRepository<FurnitureEntity, Long>, QuerydslPredicateExecutor<FurnitureEntity>, MyFurnitureRepository {

    Page<FurnitureEntity> findAll(Pageable pageable);

    FurnitureEntity findByFid(Long fid);

    @Modifying
    @Query(value = "update furniture f set f.hits = f.hits + 1 where f.furniture_id = :fid", nativeQuery = true)
    void updateHitsByFid(@Param("fid") Long fid);

    @Modifying
    @Query(value = "update furniture f set f.furniture_stock = f.furniture_stock - :amount where f.furniture_id = :fid", nativeQuery = true)
    void updateStock(Long fid, int amount);
    List<FurnitureEntity> findTop3ByOrderByHitsDesc();
}
