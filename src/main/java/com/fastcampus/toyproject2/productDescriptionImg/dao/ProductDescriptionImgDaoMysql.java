package com.fastcampus.toyproject2.productDescriptionImg.dao;


import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
@MapperScan
public class ProductDescriptionImgDaoMysql implements ProductDescriptionImgDao {

    private final SqlSession sqlSession;
    private final String namespace = "productDescriptionMapper.";


    @Override
    public List<ProductDescriptionImg> findAllByProductDescriptionId(String productDescriptionId) {
        return sqlSession.selectList(namespace+"findAllByProductDescriptionId", productDescriptionId);
    }

    @Override
    public int insert(List<ProductDescriptionImg> productDescriptionImgs) throws Exception {
        return sqlSession.insert(namespace+"saveAll", productDescriptionImgs);
    }


}
