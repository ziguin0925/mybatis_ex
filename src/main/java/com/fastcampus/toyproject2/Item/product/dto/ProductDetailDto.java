package com.fastcampus.toyproject2.Item.product.dto;

import com.fastcampus.toyproject2.Item.category.dto.CategoryHierarchyDto;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import com.fastcampus.toyproject2.Item.stock.dto.Stock;
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
    private int eventPrice;
    private ProductDescriptionDto productDescription;
    private String repImg;
    private String categoryId;
    private String categoryName;
    //자신의 카테고리 이름은 parentCategorys에 포함 되어 있음.
    private String productStatus;

    private  List<Stock>  stockList;

    private List<CategoryHierarchyDto> parentCategorys;
    //    private String productStatus;
    private int salesQuantity;

    private String registerManager;

    //url만 받아와도 되는지?
    private List<ProductDescriptionImgDetailDto> productImages;
    private List<ProductDescriptionImgDetailDto> descriptionImages;

    //review_content

    private int likeCount;
    private int starRating;
    private int reviewCount;
    private int viewCount;




}
