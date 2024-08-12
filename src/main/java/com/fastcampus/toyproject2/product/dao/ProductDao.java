package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductDetailDto;
import com.fastcampus.toyproject2.product.dto.ProductListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductDao{

    int insert(@Param("product")Product product) throws Exception;
    int insertTest(HashMap<String, Object> testMap ) throws Exception;


    String findNameById(@Param("productId") String productId)throws Exception;

    ProductDetailDto findProductDetailById(@Param("productId") String productId) throws Exception;

    List<ProductListDto> findCursorList(HashMap<String, Object> map) throws Exception;

    List<ProductListDto> findPageList(HashMap<String, Object> map) throws Exception;

    int countProduct(HashMap<String, Object> map) throws Exception;


    void deleteByProductId(@Param("productId") String productId) throws Exception;

}
