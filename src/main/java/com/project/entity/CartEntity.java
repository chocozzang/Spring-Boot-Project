package com.project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
@Repository
public class CartEntity {

    @Id @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private int Count;

    public static CartEntity createCart(MemberEntity memberEntity){
        CartEntity cartEntity =new CartEntity();
        cartEntity.memberEntity = memberEntity;
        return cartEntity;
    }



}
