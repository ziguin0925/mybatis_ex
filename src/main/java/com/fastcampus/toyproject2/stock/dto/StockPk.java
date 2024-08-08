package com.fastcampus.toyproject2.stock.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockPk {
    private String productId;
    private String size;
    private String color;
}
