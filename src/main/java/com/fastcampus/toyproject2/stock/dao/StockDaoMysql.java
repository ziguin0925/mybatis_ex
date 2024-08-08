package com.fastcampus.toyproject2.stock.dao;

import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
@MapperScan
public class StockDaoMysql implements StockDao{
    private final SqlSession sqlSession;

    private final String namespace = "stockMapper.";


    @Override
    public Stock findById(StockPk stockPk) throws Exception {
        return sqlSession.selectOne(namespace+"findById", stockPk);
    }

    @Override
    public List<Stock> findAllByProductId(String productId) throws Exception {
        return sqlSession.selectList(namespace+"findAllByProductId", productId);
    }

    @Override
    public int insert(List<Stock> stock) throws Exception{
        return sqlSession.insert(namespace+"insert",stock);
    }

    @Override
    public int delete(StockPk stockPk) throws Exception {
        return sqlSession.delete(namespace+"deleteByStockPk", stockPk);
    }

    @Override
    public int delete(String productId) throws Exception {
        return sqlSession.delete(namespace+"deleteByProductId", productId);
    }



}
