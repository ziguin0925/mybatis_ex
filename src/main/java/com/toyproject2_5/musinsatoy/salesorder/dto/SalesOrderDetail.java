package com.toyproject2_5.musinsatoy.salesorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
