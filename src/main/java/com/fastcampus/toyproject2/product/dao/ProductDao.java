package com.fastcampus.toyproject2.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao{

    String findById(@Param("productId") String id);

}
