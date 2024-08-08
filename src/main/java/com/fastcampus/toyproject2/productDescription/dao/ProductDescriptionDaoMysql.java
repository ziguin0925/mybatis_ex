package com.fastcampus.toyproject2.productDescription.dao;


import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
@MapperScan
public class ProductDescriptionDaoMysql implements ProductDescriptionDao {

    private final SqlSession sqlSession;

    private final String namespace = "productDescriptionMapper.";

    @Override
    public ProductDescription findById(String productDescriptionId) throws Exception{
        return sqlSession.selectOne(namespace+"findById", productDescriptionId);
    }

    @Override
    public int insert(ProductDescription productDescription) throws Exception {
        return sqlSession.insert(namespace+"insert", productDescription);

    }

    @Override
    public int delete(String productDescriptionId) throws Exception {
        return sqlSession.delete(namespace+"delete", productDescriptionId);
    }


}
