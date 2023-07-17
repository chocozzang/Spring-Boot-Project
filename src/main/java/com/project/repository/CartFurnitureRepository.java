package com.project.repository;

import com.project.entity.CartFurnitureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartFurnitureRepository extends JpaRepository<CartFurnitureEntity,Long> {
    CartFurnitureEntity findByCfid(Long cfid);

    @Query(value = "select cf.fid from cart_furniture cf where cf.cart_furniture_id = :cfid", nativeQuery = true)
    Long selectFid(Long cfid);
//
//    @Query(value = "select cf.furniture_amount from cart_furniture cf where cf.cart_furniture_id = :cfid", nativeQuery = true)
//    Long selectAmount(Long cfid);

    @Query(value = "select *  from cart_furniture cf where cf.cart_furniture_id = :cfid", nativeQuery = true)
    CartFurnitureEntity selectFidAndAmount(Long cfid);

    @Query(value = "select cf.cart_furniture_id from cart_furniture cf where cf.fid = :cfid", nativeQuery = true)
    Long findCFID(Long cfid);

    @Query(value = "select * from cart_furniture cf where cf.cart_id = :cid", nativeQuery = true)
    List<CartFurnitureEntity> findAllByCid(Long cid);

    @Query(value = "select * from cart_furniture cf where cf.cart_id = :cid and cf.fid = :fid", nativeQuery = true)
    CartFurnitureEntity findAnyCF(Long fid, Long cid);

    @Modifying
    @Query(value = "update cart_furniture cf set cf.furniture_amount = cf.furniture_amount + :count where cf.cart_id = :cid", nativeQuery = true)
    void updateAmounts(Long count, Long cid);

    @Query(value = "select cf.cart_furniture_id from cart_furniture cf where cf.fid = :fid", nativeQuery = true)
    Long selectCfid(Long fid);

    @Modifying
    @Query(value = "delete from cart_furniture cf where cf.fid = :fid", nativeQuery = true)
    void deleteByFid(Long fid);
}
