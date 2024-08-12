package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@MapperScan
public class BrandDaoMysql implements BrandDao{

    private final SqlSession sqlSession;

    private String namespace ="brandMapper.";

    @Override
    public BrandDto findById(String brandId) {

        return sqlSession.selectOne(namespace+"findById", brandId);
    }

    @Override
    public int update(BrandUpdateDto updateDto) throws Exception {
        return sqlSession.update(namespace+"updateBrand",updateDto);
    }

    @Override
    public int insert(BrandDto brand) throws Exception {
        return  sqlSession.insert(namespace+"insertBrand", brand);
    }


}
