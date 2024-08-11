package com.fastcampus.toyproject2.productDescriptionImg.dao;


import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDescriptionImgDao {

    List<ProductDescriptionImgDetailDto> findAllByProductDescriptionId(@Param("productDescriptionId") String productDescriptionId) throws Exception;

    int insert(List<ProductDescriptionImg> productDescriptionImgs) throws Exception;

//    void saveAll(@Param("ProductDescriptionImgList") List<ProductDescriptionImg> productDescriptionImgs);
}
