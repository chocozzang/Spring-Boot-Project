package com.project.service;

import com.project.entity.CartEntity;
import com.project.entity.CartFurnitureEntity;
import com.project.entity.MemberEntity;
import com.project.repository.CartFurnitureRepository;
import com.project.repository.CartRepository;
import com.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartFurnitureRepository cart_furnitureRepository;
    @Autowired
    MemberRepository memberRepository;

    public void createCart(MemberEntity memberEntity) {
        CartEntity cartEntity = CartEntity.createCart(memberEntity);
        cartRepository.save(cartEntity);
    }

    @Override
    public void addCart(MemberEntity memberEntity, CartFurnitureEntity cart_furnitureEntity, int count) {
        //Cart cart = cartRepository.findByUserId(member.getMid());

        CartEntity cartEntity = null;

        if (cartEntity == null) {
            cartEntity = CartEntity.createCart(null);
            cartRepository.save(cartEntity);
        }
//        Cart_Furniture cart_furniture=cart_furnitureRepository.findByCidAndCfid(cart.getCid(),cart_furniture.getCfid());

        if (cart_furnitureEntity == null) {
            cart_furnitureEntity = CartFurnitureEntity.createCartFurniture(cartEntity, cart_furnitureEntity, count);
            cart_furnitureRepository.save(cart_furnitureEntity);
        } else {
            cart_furnitureEntity.addCount(count);
        }
    }

    @Override
    public void cartPayment(Long mid) {
        List<CartFurnitureEntity> cart_furnitures = cart_furnitureRepository.findAll();
        List<CartFurnitureEntity> membercart_furnitures = new ArrayList<>();

    }


    public List<CartFurnitureEntity> userCartView(CartEntity cartEntity) {
        List<CartFurnitureEntity> cart_items = cart_furnitureRepository.findAll();
        List<CartFurnitureEntity> user_items = new ArrayList<>();

        for (CartFurnitureEntity cart_furniture : cart_items) {
            /*
            if(cart_furniture.getCart().getCid()==cart.getCid()){
                    user_items.add(cart_furniture);
            }
            */
            user_items.add(cart_furniture);
        }
        return user_items;
    }

    public void makeCart(CartEntity cartEntity) {
        cartRepository.save(cartEntity);
    }

    @Override
    public Long getCart(Long mid) {
        return cartRepository.findCartIdByMid(mid);
    }

    @Override
    public CartEntity getMyCart(Long cid) {
        return cartRepository.findByCid(cid);
    }


}
