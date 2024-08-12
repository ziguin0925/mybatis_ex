package com.fastcampus.toyproject2.productDescription.dao;

import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductDescriptionDaoMysqlTest {

    @Autowired
    private ProductDescriptionDaoMysql productDescriptionDao;

    @Test
    @Order(1)
    @DisplayName("상세설명 있는 경우")
    void getProductDescriptionIsNotNull() throws Exception {
        ProductDescriptionDto productDescription =productDescriptionDao.findById("ADIDAS0000001");

        assertNotNull(productDescription);

        System.out.println(productDescription);
    }



    @Test
    @Order(2)
    @DisplayName("상세설명 없는 경우")
    void getProductDescriptionIsNull() throws Exception {
        ProductDescriptionDto productDescription =productDescriptionDao.findById("aaaa");

        assertNull(productDescription);
    }



    @Test
    @Order(3)
    @DisplayName("상세 설명 생성")
    void insertProductDescription() throws Exception {
        ProductDescription productDescription =new ProductDescription("asdf","상세 설명을 만듦",null);
        productDescriptionDao.insert(productDescription);
        ProductDescriptionDto productDescription2 =productDescriptionDao.findById("asdf");
        assertNotNull(productDescription2);
        System.out.println(productDescription2);
    }


    @Test
    @Order(4)
    @DisplayName("상세 설명 삭제")
    void deleteProductDescription() throws Exception {
        productDescriptionDao.delete("asdf");
        ProductDescriptionDto productDescription2 =productDescriptionDao.findById("asdf");
        assertNull(productDescription2);
    }
}