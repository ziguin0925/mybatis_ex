package com.fastcampus.toyproject2.Item.productDescription.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDescription {

    private String productDescriptionId;

    private String description;

    private LocalDateTime modifyDatetime;



}
