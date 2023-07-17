package com.project.service;

import com.project.entity.CartEntity;
import com.project.entity.CartFurnitureEntity;
import com.project.entity.MemberEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

   public void createCart(MemberEntity memberEntity);

   public void addCart(MemberEntity memberEntity, CartFurnitureEntity cart_furnitureEntity, int count);

   public void cartPayment(Long mid);

   public void makeCart(CartEntity cartEntity);
   public Long getCart(Long mid);

   public CartEntity getMyCart(Long cid);


}
