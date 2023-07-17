package com.project.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "cart_furniture")
@Getter
@Setter
@ToString
@Repository
public class CartFurnitureEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cart_furniture_id")
    private Long cfid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;


    @Column(nullable = false, name = "furniture_amount")
    private Integer furnitureAmount; //수량

    @Column(nullable = false, name = "furniture_price")
    private Integer furniturePrice;

    @Column(nullable = false, name = "furniture_subtotal")
    private Integer furnitureSubtotal;

    @Column(nullable = false, name = "furniture_name")
    private String furnitureName; //가구이름

    private int count;

    private Long fid;

    private String fileURL;

//    private Long realFid;

    public void addCount(int c) {count += c;}
public static CartFurnitureEntity createCartFurniture(CartEntity cartEntity, CartFurnitureEntity cartFurnitureEntity, int count){
    CartFurnitureEntity cart_furniture = new CartFurnitureEntity();
    cart_furniture.setCartEntity(cartEntity);
    cart_furniture.setFurnitureAmount(cartFurnitureEntity.furnitureAmount);
    cart_furniture.setFurniturePrice(cartFurnitureEntity.furniturePrice);
    cart_furniture.setFurnitureSubtotal(cart_furniture.getFurniturePrice() * cart_furniture.getFurnitureAmount());
    cart_furniture.setFurnitureName(cartFurnitureEntity.furnitureName);

    return cart_furniture;
}



}



