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


    public ProductDescription findById(String productDescriptionId) throws Exception {
        return productDescriptionDao.findById(productDescriptionId);
    }


    public void save(ProductDescriptionDto productDescriptionDto
            , MultipartFile productRepImg
            , MultipartFile[] DescriptionImgs
            , MultipartFile[] productImgs) throws Exception {

        ProductDescription productDescription = ProductDescription.builder()
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .description(productDescriptionDto.getProductDescription())
                .modifyDatetime(LocalDateTime.now())
                .build();

        productDescriptionDao.insert(productDescription);


        List<ProductDescriptionImg> imgList = new ArrayList<>();
        byte i =1;

        for(MultipartFile file : DescriptionImgs){

            String filename = file.getOriginalFilename();

            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.DESCRIPTION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();

            imgList.add(productDescriptionImg);
        }

        i=1;

        for(MultipartFile file : productImgs){

            String filename = file.getOriginalFilename();
            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.REPRESENTATION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();
            imgList.add(productDescriptionImg);
        }

        productDescriptionImgDao.insert(imgList);
    }
}
