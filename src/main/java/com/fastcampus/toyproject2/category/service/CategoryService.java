package com.fastcampus.toyproject2.category.service;

import com.fastcampus.toyproject2.category.dao.CategoryDaoMysql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDaoMysql categoryDao;

    public String findById(String categoryId) {
        try {
            if(categoryDao.findById(categoryId)!=null) {
                return categoryId;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}