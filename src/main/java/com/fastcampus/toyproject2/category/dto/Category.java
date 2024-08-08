package com.fastcampus.toyproject2.category.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Category {

    private String categoryId;
    private String categoryName;
    private String parentCategoryId;


}
