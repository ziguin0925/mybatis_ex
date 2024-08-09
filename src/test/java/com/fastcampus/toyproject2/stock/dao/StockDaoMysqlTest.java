package com.fastcampus.toyproject2.stock.dao;

import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockDaoMysqlTest {
    @Autowired
    StockDaoMysql stockDao;
    @Qualifier("forceAutoProxyCreatorToUseClassProxying")
    @Autowired
    private BeanFactoryPostProcessor forceAutoProxyCreatorToUseClassProxying;

    @Test
    @Order(1)
    @DisplayName("재고 찾기")
    void findStock() throws Exception {
        //하나만 찾는 경우
        StockPk stockPk = new StockPk("P008","24","blue");
        List<Stock> stock =stockDao.findByStockPk(stockPk);
        assertNotNull(stock);
        assertTrue(stock.size()==1);

        //이터레이터 사용 고려
        assertTrue(stock.get(0).getProductId().equals("P008"));
    }

    @Test
    @Order(2)
    @DisplayName("재고 한개 생성")
    void createStock() throws Exception {
        StockPk stockPk = new StockPk("P008","24","red");
        Stock registerStock = Stock.builder()
                .size("24")
                .color("red")
                .productId("P008")
                .quantity(1200)
                .createDatetime(null)
                .build();
        List<Stock> stocks =new ArrayList<>();
        stocks.add(registerStock);

        stockDao.insert(stocks);

        List<Stock> stock =stockDao.findByStockPk(stockPk);
        assertNotNull(stock);
        assertTrue(stock.size()==1);
        assertTrue(stock.get(0).getSize().equals("24"));

    }

    @Test
    @Order(3)
    @DisplayName("재고 StockPK 로 삭제")
    void deleteStock() throws Exception {
        StockPk stockPk = StockPk.IdAndSizeAndColor("P008","24","red");
        stockDao.deleteByStockPk(stockPk);
        List<Stock> stock =stockDao.findByStockPk(stockPk);
        assertTrue(stock.size()==0);
    }

    //P004 다 없애고 시작하기.
    @Test
    @Order(4)
    @DisplayName("재고 여러개 생성")
    void createStockList() throws Exception {
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Stock stock = Stock.builder()
                    .size(Integer.toString(i))
                    .color("red")
                    .productId("P004")
                    .quantity(i)
                    .build();
            stocks.add(stock);
        }
        stockDao.insert(stocks);
        assertEquals(10,stockDao.findByStockPk(StockPk.onlyId("P004")).size());
    }

    @Test
    @Order(5)
    @DisplayName("상품 id에 대한 모든 재고 지우기")
    void  deleteByProductId() throws Exception {
        stockDao.deleteByStockPk(StockPk.onlyId("P004"));
        assertEquals(0,stockDao.findByStockPk(StockPk.onlyId("P004")).size());
    }

    @Test
    @Order(6)
    @DisplayName("StockPk 에 productId 지정 안했을 경우")
    void noProductId() {
        try {
            stockDao.findByStockPk(StockPk.builder().size("24").build());
        }catch (Exception e ){
            assertEquals("productId를 지정 하지 않았습니다.", e.getMessage());
        }
    }

}