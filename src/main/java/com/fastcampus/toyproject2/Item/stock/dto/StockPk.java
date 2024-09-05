package com.fastcampus.toyproject2.Item.stock.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class StockPk {

    public static StockPk onlyId(String productId){
         return StockPk.builder().productId(productId).build();
    }

    public static StockPk IdAndSize(String productId, String size){
        return StockPk.builder().productId(productId).size(size).build();
    }

    public static StockPk IdAndColor(String productId, String color){
        return StockPk.builder().productId(productId).color(color).build();
    }

    public static StockPk IdAndSizeAndColor(String productId, String size, String color){
        return StockPk.builder().productId(productId).color(color).size(size).build();
    }

    private String productId;
    private String size;
    private String color;

}
