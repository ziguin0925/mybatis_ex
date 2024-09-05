package com.fastcampus.toyproject2.productDescription.dao;

import com.fastcampus.toyproject2.Item.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
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
        ProductDescriptionDto productDescription2 =productDescriptionDao.findById(productDescription.getProductDescriptionId());

        assertTrue(productDescription2.getDescription().equals(productDescription.getDescription()));
        System.out.println(productDescription2);
    }


    @Test
    @Order(4)
    @DisplayName("상세 설명 삭제")
    void deleteProductDescription() throws Exception {
        ProductDescription productDescription =new ProductDescription("asdf","상세 설명을 만듦",null);
        productDescriptionDao.insert(productDescription);
        ProductDescriptionDto productDescription2 =productDescriptionDao.findById(productDescription.getProductDescriptionId());

        assertTrue(productDescription2.getDescription().equals(productDescription.getDescription()));
        System.out.println(productDescription2);


        productDescriptionDao.delete(productDescription.getProductDescriptionId());
        ProductDescriptionDto productDescriptionNull =productDescriptionDao.findById(productDescription.getProductDescriptionId());

        assertNull(productDescriptionNull);
    }

    @Test
    @Order(5)
    @DisplayName("상세설명 수정")
    void updateProductDescription() throws Exception {
        ProductDescription productDescription =new ProductDescription("asdf","상세 설명을 만듦",null);
        productDescriptionDao.insert(productDescription);
        ProductDescriptionDto productDescription2 =productDescriptionDao.findById(productDescription.getProductDescriptionId());

        assertTrue(productDescription2.getDescription().equals(productDescription.getDescription()));
        System.out.println(productDescription2);

        productDescription2.setDescription("상세 설명 수정");

        productDescriptionDao.update(productDescription2);

        ProductDescriptionDto productDescriptionUpdate =productDescriptionDao.findById(productDescription.getProductDescriptionId());


        assertTrue(productDescriptionUpdate.getDescription().equals(productDescription2.getDescription()));
    }


}