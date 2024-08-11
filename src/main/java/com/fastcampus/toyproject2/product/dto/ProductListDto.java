package com.fastcampus.toyproject2.product.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductListDto {

    private String productId;
    private String productName;
    private String price;
    private String repImg;
    private float starRating;
    private String brandId;
    private String brandName;

}
