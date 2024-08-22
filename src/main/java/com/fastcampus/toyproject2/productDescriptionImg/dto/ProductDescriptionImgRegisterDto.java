package com.fastcampus.toyproject2.productDescriptionImg.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescriptionImgRegisterDto {

    private String fileName;
    private Long fileSize;
    private String fileType;

}