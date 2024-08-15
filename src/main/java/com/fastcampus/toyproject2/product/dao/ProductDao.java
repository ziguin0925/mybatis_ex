package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductDetailDto;
import com.fastcampus.toyproject2.product.dto.ProductPageDto;
import com.fastcampus.toyproject2.product.dto.ProductUpdateDto;
import com.fastcampus.toyproject2.product.dto.pagination.cursor.ProductCursorPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductDao{

    int insertTest(@Param("product")Product product) throws Exception;
    int insert(HashMap<String, Object> testMap ) throws Exception;


    String findNameById(@Param("productId") String productId)throws Exception;

    ProductDetailDto findProductDetailById(@Param("productId") String productId) throws Exception;

    List<ProductCursorPageDto> findCursorList(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListOrderByRank(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListOrderBySales(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByHighPrice(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByLowPrice(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByLastest(HashMap<String, Object> map) throws Exception;


    List<ProductPageDto> findPageList(HashMap<String, Object> map) throws Exception;

    int countProduct(HashMap<String, Object> map) throws Exception;


    int updateProduct(@Param("product")ProductUpdateDto productUpdateDto) throws Exception;


    int deleteByProductId(@Param("productId") String productId) throws Exception;



}
