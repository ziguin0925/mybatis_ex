package com.fastcampus.toyproject2.product.dto;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
@NoArgsConstructor
@Getter
public class ProductRegisterDto {

    //중복되는 상품 ID 가 있는지 확인.(등록자가 상품ID 등록?)
    //실제 상품ID를 받고, 실제 상품 ID 뒤에 번호 등 추가하기.
    private String productId;

    private String name;

    //해당 브랜드가 있는지 확인.
    private String BrandId;

    //목록에 나타낼 대표 사진 이외 모든 상세 설명 재사용 할지 여부.
    //기존의 product_description_id을 사용하지 않는다면 받아오기.
    private ProductDescriptionDto productDescriptionDto;

    private String repImg;

    private String categoryId;

    private int price;
    private String managerName;

    //재고- 사이즈, 컬러, 재고 수량 받기
    private List<String> size= new ArrayList<>();
    private List<String> color= new ArrayList<>();
    private List<Integer> quantity= new ArrayList<>();


    @Builder
    public ProductRegisterDto(String productId, String name, String brandId, ProductDescriptionDto productDescriptionDto, String repImg, String categoryId, int price, String managerName, List<String> size, List<String> color, List<Integer> quantity) {
        this.productId = productId;
        this.name = name;
        this.productDescriptionDto = productDescriptionDto;
        this.BrandId = brandId;
        this.repImg = repImg;
        this.categoryId = categoryId;
        this.price = price;
        this.managerName = managerName;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
