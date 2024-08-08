package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao{

    String findNameById(@Param("productId") String productId)throws Exception;

    int insert(@Param("product")Product product) throws Exception;

    void deleteByProductId(@Param("productId") String productId) throws Exception;

}
