package com.toyproject2_5.musinsatoy.Item.product.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPageDto {

    private String productId;
    private String productName;
    private int price;
    private String repImg;
    private int starRating;
    private String brandId;
    private String brandName;

}
