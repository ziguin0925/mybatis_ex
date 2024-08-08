package com.fastcampus.toyproject2.category.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Repository
@MapperScan
public class CategoryDaoMysql implements CategoryDao {
    private final SqlSession sqlSession;

    private String namespace ="categoryMapper.";

    @Override
    public String findById(String categoryId) throws Exception {
        return sqlSession.selectOne(namespace+"findById", categoryId);
    }
}
