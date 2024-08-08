package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.Brand;
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
    public Brand findById(String brandId) {

        return sqlSession.selectOne(namespace+"findById", brandId);
    }





}
