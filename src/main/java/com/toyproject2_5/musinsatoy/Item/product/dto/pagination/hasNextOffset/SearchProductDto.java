package com.toyproject2_5.musinsatoy.Item.product.dto.pagination.hasNextOffset;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductDto {
    //Getparameter 로 받아올 값.

    private String keyword;
    private String categoryId;
    private String sortCode;
    private int page;
    private int size; // limit

    //직접 설정 (page-1)*size;
    private int offset;

    public void calOffset(){
        offset = (page - 1) * size;
    }
}
