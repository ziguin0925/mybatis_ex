package com.fastcampus.toyproject2.salesorder.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class SalesOrder {
    // 주문
    private String orderId;
    private int memberId;
    private LocalDateTime orderDatetime;
    private int stateCode;
    // 배송
    private String recipient;
    private String phone;
    private String addressA;
    private String addressB;
    private String postcode;
    private String deliveryRequest;
    // 결제
    private Long paymentId;
    private String paymentMethod;
    private int paymentAmount;
    // 주문 상세
    private List<SalesOrderDetail> salesOrderDetailList;

    // Service에서 orderId 생성해서 set 할 때 필요
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // MyBatis 자동 매핑에 필요
    public void setSalesOrderDetailList(List<SalesOrderDetail> salesOrderDetailList) {
        this.salesOrderDetailList = salesOrderDetailList;
    }
}