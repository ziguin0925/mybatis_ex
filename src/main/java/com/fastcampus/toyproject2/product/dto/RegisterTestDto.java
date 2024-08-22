package com.fastcampus.toyproject2.product.dto;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgRegisterDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterTestDto {
    @Size(min = 10, max = 25, message = "상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
    @NotNull(message = "상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
    private String productId;

    //몇글자 이상으로 받을지 생각
    @Size(min =3 , max =25, message = "상품 이름은 3글자 이상, 25자 미만 이어야 합니다.")
    @NotNull(message = "상품 이름은 3글자 이상, 25자 미만 이어야 합니다.")
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
    private int price;

    @Size(min=2, max =20, message = "상품 등록자 이름은 2글자 이상, 20글자 이하여야 합니다.")
    @NotBlank(message = "Product manager name is not defined")
    private String managerName;

    //재고- 사이즈, 컬러, 재고 수량 받기
    @NotNull(message = "Product size is not defined")
    private List<String> size= new ArrayList<>();

    @NotNull(message = "Product color is not defined")
    private List<String> color= new ArrayList<>();

    @NotNull(message = "Product quantity is not defined")
    private List<Integer> quantity= new ArrayList<>();


    private String repImg; // 대표 이미지 파일 이름
    private List<ProductDescriptionImgRegisterDto> desImgs; // 설명 이미지 리스트
    private List<ProductDescriptionImgRegisterDto> represenImgs;
}
