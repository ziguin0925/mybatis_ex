package com.fastcampus.toyproject2.productDescriptionImg.dao;

import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductDescriptionImgDaoMysqlTest {
    @Autowired
    private ProductDescriptionImgDao productDescriptionImgDao;

    @Test
    @Order(1)
    @DisplayName("조회")
    void findBy() throws Exception {

        String productDescriptionId = "";
        productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionId);

    }

    @Test
    @Order(2)
    @DisplayName("삭제")
    void delete() throws Exception {
        String productDescriptionId = "  ";
        productDescriptionImgDao.deleteByProductDesciprionId(productDescriptionId);
    }

}