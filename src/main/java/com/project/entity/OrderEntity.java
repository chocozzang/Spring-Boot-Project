package com.project.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "furniture_order")
@Getter
@Setter
@ToString
@Repository
public class OrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long oid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @Column(nullable = false, name = "order_name")
    private String orderName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false, name = "cart_total")
    private Long cartTotal;

    public static OrderEntity createOrder(MemberEntity memberEntity, CartEntity cartEntity){
        OrderEntity orderEntity =new OrderEntity();
        orderEntity.setOrderName(memberEntity.getName());
        orderEntity.setAddress(memberEntity.getAddress());
        orderEntity.setZipcode(memberEntity.getZipcode());

        return orderEntity;

    }
}
