package com.project.repository;

import com.project.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    static void assign(CartEntity cartEntity) {
    }

    CartEntity findByCid(Long cid);

    @Modifying
    @Query(value = "delete from cf cart_furniture where cf.furniture_id = :fid" ,nativeQuery = true)
    void delete(Long fid);

    @Query(value = "select c.cart_id from cart c where c.member_id = :mid", nativeQuery = true)
    Long findCartIdByMid(Long mid);
}
