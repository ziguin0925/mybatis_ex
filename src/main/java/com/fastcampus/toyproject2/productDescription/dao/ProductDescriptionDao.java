package com.fastcampus.toyproject2.productDescription.dao;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDescriptionDao {


    ProductDescription findById(@Param("productDescriptionId") String productDescriptionId)throws Exception;
    int insert(ProductDescription productDescription) throws Exception;
    int delete(@Param("productDescriptionId") String productDescriptionId) throws Exception;
    ProductDescriptionDto findByProductId(@Param("productId") String productId) throws Exception;
}
