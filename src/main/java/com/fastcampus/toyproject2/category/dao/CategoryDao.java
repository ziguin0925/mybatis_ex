package com.fastcampus.toyproject2.category.dao;

import com.fastcampus.toyproject2.category.dto.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryDao {

    int insert(Category category) throws Exception;

    String findById(@Param("categoryId")  String categoryId) throws Exception;

    int update(Category category) throws Exception;

    int deleteByCategoryId(@Param("categoryId") String categoryId) throws  Exception;



}
