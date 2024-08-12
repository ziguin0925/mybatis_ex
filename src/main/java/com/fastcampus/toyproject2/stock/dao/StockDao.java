package com.fastcampus.toyproject2.stock.dao;

import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDao {

    int insert(@Param("Stock") List<Stock> stock) throws Exception;

    List<Stock> findByStockPk(@Param("StockPk") StockPk stockPk )throws Exception;

    int update(@Param("Stock") Stock stock) throws Exception;


    int deleteByStockPk(@Param("StockPk") StockPk stockPk)throws Exception;





}
