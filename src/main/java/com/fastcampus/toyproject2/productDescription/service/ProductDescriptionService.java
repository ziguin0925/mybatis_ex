package com.fastcampus.toyproject2.productDescription.service;


import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDao;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDescriptionService {

    private final ProductDescriptionDaoMysql productDescriptionDao;
    private final ProductDescriptionImgDaoMysql productDescriptionImgDao;
    private final FileService fileService;

    @Value("${productImgLocation}")
    private String imgLocation;


    public ProductDescriptionDto findById(String productDescriptionId) throws Exception {
        return productDescriptionDao.findById(productDescriptionId);
    }


    public void save(ProductDescriptionDto productDescriptionDto
            , List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs) throws Exception {

         List <ProductDescriptionImg> imgList = fileService.toImageList(DescriptionImgs, productImgs, productDescriptionDto.getProductDescriptionId());

        productDescriptionImgDao.insert(imgList);
    }
}
