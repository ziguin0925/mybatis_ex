package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandDao {

    int insert(BrandCreateDto brand) throws Exception;

    BrandDto findById(@Param("brandId") String brandId);


    int update(BrandUpdateDto updateDto) throws  Exception;



    int delete(String brandId) throws Exception;








}
