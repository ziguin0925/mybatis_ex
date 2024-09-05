package com.fastcampus.toyproject2.Item.category.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Category {

    public static String FIRSTCATEGORY = "C01";

    private String categoryId;
    private String categoryName;
    private String parentCategoryId;


}
