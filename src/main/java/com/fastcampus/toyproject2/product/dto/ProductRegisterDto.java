package com.fastcampus.toyproject2.product.dto;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
@NoArgsConstructor
@Getter
public class ProductRegisterDto {
    /*
    @NotNull - "", " " 은 허용

    @NotEmpty - "", null 허용 안함.

    @NotBlank - "", " ", null 허용안함.
    */
    //중복되는 상품 ID 가 있는지 확인.(등록자가 상품ID 등록?)
    //실제 상품ID를 받고, 실제 상품 ID 뒤에 번호 등 추가하기.
    @Size(min = 10, max = 25, message = "상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
    @NotNull(message = "상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
    private String productId;

    //몇글자 이상으로 받을지 생각
    @Size(min =3 , max =25, message = "3글자 이상, 25자 미만 이어야 합니다.")
    @NotNull(message = "3글자 이상, 25자 미만 이어야 합니다.")
    private String name;

    //해당 브랜드가 있는지 확인.
    @NotBlank(message = "Product brand is not defined")
    private String BrandId;

    //목록에 나타낼 대표 사진 이외 모든 상세 설명 재사용 할지 여부.
    //기존의 product_description_id을 사용하지 않는다면 받아오기.
    private ProductDescriptionDto productDescriptionDto;

    @NotBlank(message = "Product category is not defined")
    private String categoryId;

    @Positive
    @NotNull(message = "Product price is not defined")
    private Integer price;

    @Size(min=2, max =20, message = "2글자 이상, 20글자 이하여야 합니다.")
    @NotBlank(message = "Product manager name is not defined")
    private String managerName;

    //재고- 사이즈, 컬러, 재고 수량 받기
    @NotNull(message = "Product size is not defined")
    private List<String> size= new ArrayList<>();

    @NotNull(message = "Product color is not defined")
    private List<String> color= new ArrayList<>();

    @NotNull(message = "Product quantity is not defined")
    private List<Integer> quantity= new ArrayList<>();


    @Builder
    public ProductRegisterDto(String productId, String name, String brandId, ProductDescriptionDto productDescriptionDto, String repImg, String categoryId, int price, String managerName, List<String> size, List<String> color, List<Integer> quantity) {
        this.productId = productId;
        this.name = name;
        this.productDescriptionDto = productDescriptionDto;
        this.BrandId = brandId;
        this.categoryId = categoryId;
        this.price = price;
        this.managerName = managerName;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
