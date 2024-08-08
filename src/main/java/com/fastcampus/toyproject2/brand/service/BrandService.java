package com.fastcampus.toyproject2.brand.service;


import com.fastcampus.toyproject2.brand.dao.BrandDaoMysql;
import com.fastcampus.toyproject2.brand.dto.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandDaoMysql brandDao;

    public Brand  findById(String id) {
        return brandDao.findById(id);
    }


}
