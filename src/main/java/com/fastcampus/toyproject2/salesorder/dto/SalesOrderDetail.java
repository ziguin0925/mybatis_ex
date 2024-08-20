package com.fastcampus.toyproject2.salesorder.dto;

import lombok.*;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class SalesOrderDetail {
    private String orderId;
    private String productId;
    private String size;
    private String color;
    private int price;
    private int amount;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
