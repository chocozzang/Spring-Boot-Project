package com.project.service;

import com.project.entity.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
//    public void create_order(CartFurniture cartFurniture, Furniture furniture);

    public void OrderPayment(OrderEntity orderEntity);

    public void DeleteCart(Long cfid);
}
