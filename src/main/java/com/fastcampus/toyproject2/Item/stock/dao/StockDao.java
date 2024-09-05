package com.fastcampus.toyproject2.Item.stock.dao;

import com.fastcampus.toyproject2.Item.stock.dto.Stock;
import com.fastcampus.toyproject2.Item.stock.dto.StockPk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockDao {

    int insert(@Param("Stock") List<Stock> stock) throws Exception;

    List<Stock> findByStockPk(@Param("StockPk") StockPk stockPk )throws Exception;

    int update(@Param("Stock") Stock stock) throws Exception;


    int deleteByStockPk(@Param("StockPk") StockPk stockPk)throws Exception;





}
