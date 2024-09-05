package com.fastcampus.toyproject2.Item.productDescription.service;


import com.fastcampus.toyproject2.Item.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.ETC.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
