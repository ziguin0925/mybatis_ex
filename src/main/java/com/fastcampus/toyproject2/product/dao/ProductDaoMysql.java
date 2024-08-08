package com.fastcampus.toyproject2.product.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@MapperScan
public class ProductDaoMysql implements ProductDao {

    private final SqlSession sqlSession;

    @Override
    public String findById(String id) {

        return sqlSession.selectOne("productMapper.findById", id);
    }



}
