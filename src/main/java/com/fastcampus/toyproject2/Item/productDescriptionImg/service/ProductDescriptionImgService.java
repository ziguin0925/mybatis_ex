package com.fastcampus.toyproject2.Item.productDescriptionImg.service;


import com.fastcampus.toyproject2.Item.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDescriptionImgService {

    private final ProductDescriptionImgDaoMysql productDescriptionImgDao;

    public List<String> findAllByProductDescriptionId(String productDescriptionId) throws Exception {
        List<ProductDescriptionImgDetailDto> descriptionimgList = productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionId);
        List<String> list = new ArrayList<>();
        for (ProductDescriptionImgDetailDto descriptionImg : descriptionimgList) {
           list.add(descriptionImg.getPath());
        }
        return list;
    }
}
