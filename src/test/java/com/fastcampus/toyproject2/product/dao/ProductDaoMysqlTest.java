package com.fastcampus.toyproject2.product.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDaoMysqlTest {

    @Autowired
    ProductDaoMysql productDao;

    @Test
    @Order(1)
    @DisplayName("Dao Test")
    public void getProductDao() {

        System.out.println(productDao.findById("P001"));
    }
}