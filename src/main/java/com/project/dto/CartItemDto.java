package com.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CartItemDto {

    private Long cart_id;//장바구니 번호
    private Long cart_total;//총 가격
    private Long member_id;//

    @Builder
    public CartItemDto(Long cart_id, Long cart_total, Long member_id){
        this.cart_id=cart_id;
        this.cart_total=cart_total;
        this.member_id=member_id;
    }

}
