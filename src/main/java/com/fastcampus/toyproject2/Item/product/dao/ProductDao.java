package com.fastcampus.toyproject2.Item.product.dao;

import com.fastcampus.toyproject2.Item.product.dto.*;
import com.fastcampus.toyproject2.Item.product.dto.pagination.cursor.ProductCursorPageDto;
import com.fastcampus.toyproject2.product.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProductDao{

    int insertTest(@Param("product") Product product) throws Exception;
    int insert(HashMap<String, Object> testMap ) throws Exception;


    String findNameById(@Param("productId") String productId)throws Exception;
    String findRepImgById(@Param("productId") String productId)throws Exception;

    ProductDetailDto findProductDetailById(@Param("productId") String productId) throws Exception;

    List<ProductAdminList> findProductAdminList() throws Exception;

    List<ProductCursorPageDto> findCursorList(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListOrderByRank(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListOrderBySales(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByHighPrice(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByLowPrice(HashMap<String, Object> map) throws Exception;
    List<ProductCursorPageDto> findCursorPageListByLastest(HashMap<String, Object> map) throws Exception;


    List<ProductPageDto> findPageList(HashMap<String, Object> map) throws Exception;

    int countProduct(HashMap<String, Object> map) throws Exception;


    int updateProduct(@Param("product") ProductUpdateDto productUpdateDto) throws Exception;
    int updateViewCount(@Param("productId")String productId) throws Exception;

    int deleteByProductId(@Param("productId") String productId) throws Exception;



}
