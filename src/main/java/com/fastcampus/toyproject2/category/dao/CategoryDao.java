package com.fastcampus.toyproject2.category.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryDao {

    String findById(@Param("categoryId")  String categoryId) throws Exception;


}
