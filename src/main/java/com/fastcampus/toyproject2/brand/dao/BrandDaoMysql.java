package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandListDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@MapperScan
public class BrandDaoMysql implements BrandDao{

    private final SqlSession sqlSession;

    private String namespace ="brandMapper.";

    @Override
    public int insert(BrandCreateDto brand) throws Exception {
        return  sqlSession.insert(namespace+"insertBrand", brand);
    }

    @Override
    public BrandDto findById(String brandId) {
        return sqlSession.selectOne(namespace+"findById", brandId);
    }

    @Override
    public List<BrandListDto> findAll() {
        return sqlSession.selectList(namespace+"findAll");
    }

    @Override
    public int update(BrandUpdateDto updateDto) throws Exception {
        return sqlSession.update(namespace+"updateBrand",updateDto);
    }

    @Override
    public int delete(String brandId) throws Exception {
        return sqlSession.delete(namespace+"deleteById", brandId);
    }

}
