package com.fastcampus.toyproject2.brand.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Brand {

    private String brandId;

    private String name;

    private String img;

    private int ProductNum;

    private LocalDateTime createDateTime;

    private LocalDateTime modifyDatetime;


}
