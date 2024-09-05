package com.fastcampus.toyproject2.Item.stock.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Stock {

    private String productId;
    private String size;
    private String color;
    private Integer quantity;
    private LocalDateTime createDatetime;


}
