package com.fastcampus.toyproject2.product.dto;


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
    private float starRating;
    private String brandId;
    private String brandName;

}
