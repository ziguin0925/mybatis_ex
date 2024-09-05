package com.fastcampus.toyproject2.Item.category.dao;

import com.fastcampus.toyproject2.Item.category.dto.Category;
import com.fastcampus.toyproject2.Item.category.dto.CategoryHierarchyDto;
import com.fastcampus.toyproject2.Item.category.dto.SubCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {

    int insert(Category category) throws Exception;

    String findById(@Param("categoryId")  String categoryId) throws Exception;
    List<CategoryHierarchyDto> findUpperCategoryHierarchyById(@Param("categoryId") String categoryId ) throws Exception;
    List<CategoryHierarchyDto> findLowerCategoryHierarchyById( @Param("categoryId") String categoryId ) throws Exception;

    List<SubCategoryDto> findSubCategoryById(@Param("categoryId") String categoryId ) throws Exception;

    int update(Category category) throws Exception;

    int deleteByCategoryId(@Param("categoryId") String categoryId) throws  Exception;



}
