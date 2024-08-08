package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface BrandDao {

    Brand findById(@Param("brandId") String brandId);
}
