package com.fastcampus.toyproject2.brand.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BrandDto {

    //브랜드 id 무신사의 경우 영어이름으로 해둠.
    @Size(min = 3, max = 25, message = "브랜드 Id는 3글자 이상, 25글자 이하여야 합니다.")
    @NotNull(message = "브랜드 Id 는 null이 되면 안됩니다.")
    private String brandId;

    @Size(min = 3, max = 25, message = "브랜드 이름은 3글자 이상, 25글자 이하여야 합니다.")
    @NotNull(message = "브랜드 Id는 null이 되면 안됩니다.")
    private String name;

    //브랜드 대표 이미지 경로.
    //따로 받아올건지 생각.
//    @Size(min = 3, max = 500, message = "이미지의 경로 에러")
    private String img;

//    private LocalDateTime createDateTime;
//
//    private LocalDateTime modifyDatetime;


}