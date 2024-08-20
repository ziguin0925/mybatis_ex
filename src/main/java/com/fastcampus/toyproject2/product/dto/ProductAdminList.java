package com.fastcampus.toyproject2.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductAdminList {
    private String productId;
    private String name;
    private String productStatus;
    private String repImg;
    private LocalDateTime createDatetime;
}
