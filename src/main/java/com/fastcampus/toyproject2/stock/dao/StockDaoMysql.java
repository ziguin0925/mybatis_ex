package com.fastcampus.toyproject2.stock.dao;

import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
@MapperScan
public class StockDaoMysql implements StockDao{
    private final SqlSession sqlSession;

    private final String namespace = "stockMapper.";


    @Override
    public List<Stock> findByStockPk(StockPk stockPk) throws Exception {
        if(stockPk.getProductId()==null){
            throw new InvalidPropertiesFormatException("productId를 지정 하지 않았습니다.");
        }
        return sqlSession.selectList(namespace+"selectByStockPk", stockPk);
    }


    @Override
    public int insert(List<Stock> stock) throws Exception{
        return sqlSession.insert(namespace+"insert",stock);
    }

    @Override
    public int deleteByStockPk(StockPk stockPk) throws Exception {

        if(stockPk.getProductId()==null){
            throw new InvalidPropertiesFormatException("productId를 지정 하지 않았습니다.");
        }

        return sqlSession.delete(namespace+"deleteByStockPk", stockPk);
    }




}
