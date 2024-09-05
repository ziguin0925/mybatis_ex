package com.fastcampus.toyproject2.Item.stock.dao;

import com.fastcampus.toyproject2.Item.stock.dto.Stock;
import com.fastcampus.toyproject2.Item.stock.dto.StockPk;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.InvalidPropertiesFormatException;
import java.util.List;


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
    public int update(Stock stock) throws Exception {
        if(stock.getProductId()==null || stock.getColor()==null || stock.getSize()==null){
            throw new InvalidPropertiesFormatException("Id, color, size는 필수 값입니다. 지정 하지 않았습니다.");
        }
        return sqlSession.update(namespace+"updateByStock", stock);
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
