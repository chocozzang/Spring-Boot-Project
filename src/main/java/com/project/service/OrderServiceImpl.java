package com.project.service;

import com.project.entity.CartEntity;
import com.project.entity.OrderEntity;
import com.project.repository.CartFurnitureRepository;
import com.project.repository.CartRepository;
import com.project.repository.MemberRepository;
import com.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartEntity cartEntity;
    @Autowired
    CartFurnitureRepository cartFurnitureRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;



//    @Override
//    public void create_order(CartFurniture cartFurniture, Furniture furniture) {
//       /*
//            furniture => db)furniture에서 받아옴
//            cartfurniture -> amount furniture id -> cf
//            furniture -> stock -= amount
//            furniture' save
//            cf->delete
//        */
//
////        cartRepository.save(furniture.getFurnitureStock());
//        cartRepository.delete(furniture.getFid());
//
//    }



    @Override
    public void OrderPayment(OrderEntity orderEntity) {

        List<OrderEntity> orderList=orderRepository.findAll();
    }

    @Override
    public void DeleteCart(Long cfid) {
        cartFurnitureRepository.deleteById(cfid);
    }


}
