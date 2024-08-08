package com.fastcampus.toyproject2.productDescription.dao;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDescriptionDao {


    ProductDescription findById(@Param("productDescriptionId") String productDescriptionId)throws Exception;
    int insert(ProductDescription productDescription) throws Exception;
    int delete(@Param("productDescriptionId") String productDescriptionId) throws Exception;
}
