package com.fastcampus.toyproject2.productDescriptionImg.dao;

import com.fastcampus.toyproject2.Item.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ProductDescriptionImgDaoMysqlTest {
    @Autowired
    private ProductDescriptionImgDaoMysql productDescriptionImgDao;

    @Test
    @Order(1)
    @DisplayName("조회")
    void findBy() throws Exception {
        String productDescriptionId = "nothing";
        assertTrue(productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionId).size()==0);

    }

    @Test
    @Order(2)
    @DisplayName("생성")
    void create() throws Exception {

    }


    @Test
    @Order(4)
    @DisplayName("삭제")
    void delete() throws Exception {
        String productDescriptionId = "NIKE000000001";
        productDescriptionImgDao.deleteByProductDesciprionId(productDescriptionId);

        assertTrue(productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionId).size()==0);
    }

}