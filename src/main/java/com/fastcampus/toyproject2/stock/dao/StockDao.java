package com.fastcampus.toyproject2.stock.dao;

import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockDao {

    Stock findById(@Param("StockPk") StockPk stockPk )throws Exception;

    int insert(@Param("Stock") List<Stock> stock) throws Exception;

    int delete(@Param("StockPk") StockPk stockPk)throws Exception;

    int delete(@Param("productId") String productId)throws Exception;

    List<Stock> findAllByProductId(@Param("productId") String productId)throws Exception;

}
