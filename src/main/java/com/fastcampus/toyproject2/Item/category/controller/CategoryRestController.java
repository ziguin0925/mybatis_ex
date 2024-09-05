package com.fastcampus.toyproject2.Item.category.controller;

import com.fastcampus.toyproject2.Item.category.dto.SubCategoryDto;
import com.fastcampus.toyproject2.Item.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/{categoryId}/subcategory")
    public ResponseEntity<?> getSubcategories(@PathVariable String categoryId) throws Exception {

        List<SubCategoryDto>  categoryList = categoryService.findSubCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }


}
