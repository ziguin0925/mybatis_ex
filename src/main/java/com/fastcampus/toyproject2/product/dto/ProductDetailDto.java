package com.fastcampus.toyproject2.product.dto;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDetailDto {


    private String productId;
    private String Name;
    private String brandId;
    private String brandName;
    private int price;
    private ProductDescriptionDto productDescription;
    private String repImg;
    private String categoryId;
    private String categoryName;

    private List<String> parentCategoryIds;
    private List<String> parentCategoryNames;
    //    private String productStatus;
    private int salesQuantity;

    private String registerManager;

    //url만 받아와도 되는지?
    private List<ProductDescriptionImgDetailDto> productImages;
    private List<ProductDescriptionImgDetailDto> descriptionImages;

    //review_content

    private int likeCount;
    private float starRating;
    private int reviewCount;
    private int viewCount;

}
