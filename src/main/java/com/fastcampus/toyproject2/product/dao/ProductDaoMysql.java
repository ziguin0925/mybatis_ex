package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@MapperScan
public class ProductDaoMysql implements ProductDao {

    private final SqlSession sqlSession;

    private String namespace ="productMapper.";

    @Override
    public String findNameById(String id) throws Exception {
        return sqlSession.selectOne(namespace+"findById", id);
    }

    @Override
    public int insert(Product product) throws Exception{
        return sqlSession.insert(namespace+"insert", product);
    }

    @Override
    public void deleteByProductId(String productId) throws Exception {
        sqlSession.delete(namespace + "deleteByProductId", productId);
    }


}
