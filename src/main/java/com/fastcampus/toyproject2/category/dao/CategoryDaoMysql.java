package com.fastcampus.toyproject2.category.dao;

import com.fastcampus.toyproject2.category.dto.Category;
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
    public int insert(Category category) throws Exception {
        return sqlSession.insert(namespace + "insertCategory", category);
    }

    @Override
    public String findById(String categoryId) throws Exception {
        return sqlSession.selectOne(namespace+"findNameById", categoryId);
    }

    @Override
    public int update(Category category) throws Exception {
        return sqlSession.update(namespace + "updateCategory", category);
    }

    @Override
    public int deleteByCategoryId(String categoryId) throws Exception {
        return sqlSession.delete(namespace + "deleteByCategoryId", categoryId);
    }
}
