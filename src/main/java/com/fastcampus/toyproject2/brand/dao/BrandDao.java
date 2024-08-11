package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandDao {

    Brand findById(@Param("brandId") String brandId);
}
