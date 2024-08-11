package com.fastcampus.toyproject2.brand.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Brand {

    //브랜드 id 무신사의 경우 영어이름으로 해둠.
    @Size(min = 5, max = 25)
    private String brandId;

    private String name;

    //브랜드 대표 이미지.
    private String img;

    private int ProductNum;

//    private LocalDateTime createDateTime;
//
//    private LocalDateTime modifyDatetime;


}
