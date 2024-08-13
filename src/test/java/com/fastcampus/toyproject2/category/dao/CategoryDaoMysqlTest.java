package com.fastcampus.toyproject2.category.dao;

import com.fastcampus.toyproject2.category.dto.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryDaoMysqlTest {
    @Autowired
    private CategoryDaoMysql categoryDao;


    @Test
    @DisplayName("카테고리 생성")
    void createCategory() throws Exception {

        Category category = new Category();
        category.setCategoryId("C99");
        category.setCategoryName("테스트 코드");
        category.setParentCategoryId("C03");

        categoryDao.insert(category);

        assertTrue(categoryDao.findById("C99").equals("테스트 코드"));
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() throws Exception {

        Category category = new Category();
        category.setCategoryId("C99");
        category.setCategoryName("테스트 코드");
        category.setParentCategoryId("C01");

        categoryDao.insert(category);
        assertTrue(categoryDao.findById(category.getCategoryId()).equals(category.getCategoryName()));

        category.setCategoryName("수정 코드");
        category.setParentCategoryId("C03");

        categoryDao.update(category);

        assertTrue(categoryDao.findById(category.getCategoryId()).equals(category.getCategoryName()));

    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() throws Exception {
        Category category = new Category();
        category.setCategoryId("C99");
        category.setCategoryName("테스트 코드");
        category.setParentCategoryId("C10");

        categoryDao.insert(category);
        assertTrue(categoryDao.findById(category.getCategoryId()).equals(category.getCategoryName()));


        String categoryId = "C99";

        categoryDao.deleteByCategoryId(categoryId);

        assertTrue(categoryDao.findById(category.getCategoryId()) == null);

    }

}