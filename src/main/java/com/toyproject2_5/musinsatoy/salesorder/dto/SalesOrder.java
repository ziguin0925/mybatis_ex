package com.toyproject2_5.musinsatoy.salesorder.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class SalesOrder {
    // 주문
    @Size(max = 30, message = "ORDER_ID는 30자를 초과할 수 없습니다.")
    private String orderId;

    @Positive(message = "memberId는 양수여야 합니다.")
    private int memberId;

    private LocalDateTime orderDatetime;

    @Min(value = 0, message = "stateCode는 0 이상의 값이어야 합니다.")
    @Max(value = 255, message = "stateCode는 255 이하의 값이어야 합니다.")
    private int stateCode;


    // 배송
    @Size(max = 30, message = "RECIPIENT는 30자를 초과할 수 없습니다.")
    private String recipient;

    @Pattern(regexp = "^\\d{10,15}$", message = "PHONE은 10~15자리 숫자여야 합니다.")
    private String phone;

    @Size(max = 80, message = "ADDRESS_A는 80자를 초과할 수 없습니다.")
    private String addressA;

    @Size(max = 80, message = "ADDRESS_B는 80자를 초과할 수 없습니다.")
    private String addressB;

    @Pattern(regexp = "^\\d{5}$", message = "POSTCODE는 반드시 5자리 숫자여야 합니다.")
    private String postcode;

    @Size(max = 80, message = "DELIVERY_REQUEST는 80자를 초과할 수 없습니다.")
    private String deliveryRequest;


    // 결제
    private Long paymentId;

    @Size(max = 15, message = "PAYMENT_METHOD는 15자를 초과할 수 없습니다.")
    private String paymentMethod;

    @PositiveOrZero(message = "PAYMENT_AMOUNT는 0 이상의 값이어야 합니다.")
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