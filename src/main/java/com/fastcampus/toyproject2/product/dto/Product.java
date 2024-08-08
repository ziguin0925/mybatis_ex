package com.fastcampus.toyproject2.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    // 제품 상태를 isDisplayed가 아니라 enum으로 상태를 나타낼지 생각.

    private String productId;

    //fk
    private String productDescriptionId;

    //fk
    private String categoryId;

    //fk
    private String brandId;

    private String name;

    private String repImg;

    private int productPrice;

    private String isDisplayed;

    private LocalDateTime createDatetime;

    private String modifyDatetime;

    private long salesQuantity;

    private String registerManager;

    private float starRating;

    private int viewCount;

    private int reviewCount;

    @Builder
    public Product(String productId, String productDescriptionId, String categoryId, String brandId, String name, String repImg, int productPrice, String isDisplayed, LocalDateTime createDatetime, String modifyDatetime, long salesQuantity, String registerManager, float starRating, int viewCount, int reviewCount) {
        this.productId = productId;
        this.productDescriptionId = productDescriptionId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.name = name;
        this.repImg = repImg;
        this.productPrice = productPrice;
        this.isDisplayed = isDisplayed;
        this.createDatetime = createDatetime;
        this.modifyDatetime = modifyDatetime;
        this.salesQuantity = salesQuantity;
        this.registerManager = registerManager;
        this.starRating = starRating;
        this.viewCount = viewCount;
        this.reviewCount = reviewCount;
    }
}