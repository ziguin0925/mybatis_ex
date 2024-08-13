package com.fastcampus.toyproject2.brand.dao;

import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BrandDaoMysqlTest {

    @Autowired
    private BrandDaoMysql brandDao;



    @Test
    @Order(1)
    @DisplayName("브랜드 생성")
    void registerBrand() throws Exception {
        BrandCreateDto brandCreateDto = new BrandCreateDto();
        brandCreateDto.setImg("c/imges/brand.png");
        brandCreateDto.setName("어쩌구브랜드");
        brandCreateDto.setBrandId("FooBarBrand");

        brandDao.insert(brandCreateDto);

        assertTrue(brandDao.findById(brandCreateDto.getBrandId()).getName().equals(brandCreateDto.getName()));

    }


    @Test
    @Order(2)
    @DisplayName("브랜드 수정")
    void updateBrand() throws Exception {
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto();
        brandUpdateDto.setBrandId("A00000000001");
        brandUpdateDto.setName("affaf");
        brandDao.update(brandUpdateDto);

        assertTrue(brandDao.findById(brandUpdateDto.getBrandId()).getName().equals(brandUpdateDto.getName()));




    }

    @Test
    @Order(3)
    @DisplayName("브랜드 삭제")
    void deleteBrand() throws Exception {

        BrandCreateDto brandCreateDto = new BrandCreateDto();
        brandCreateDto.setImg("c/imges/brand.png");
        brandCreateDto.setName("어쩌구브랜드");
        brandCreateDto.setBrandId("FooBarBrand");

        brandDao.insert(brandCreateDto);

        assertEquals(brandDao.findById(brandCreateDto.getBrandId()).getName(),  brandCreateDto.getName());



        brandDao.delete(brandCreateDto.getBrandId());

        assertTrue(brandDao.findById(brandCreateDto.getBrandId()) == null);
    }

}