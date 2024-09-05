package com.fastcampus.toyproject2.Item.brand.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandListDto {
    private String brandId;
    private String name;
    private String img;
    private String salesCount;
}
