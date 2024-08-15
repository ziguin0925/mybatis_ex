package com.fastcampus.toyproject2.brand.service;


import com.fastcampus.toyproject2.brand.dao.BrandDaoMysql;
import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import com.fastcampus.toyproject2.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.product.dto.ProductPageDto;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandDaoMysql brandDao;
    private final FileService fileService;
    private final ProductDaoMysql productDao;

    @Transactional(readOnly = true)
    public BrandDto findById(String id) {
        return brandDao.findById(id);
    }

    public int createBrand(BrandCreateDto brand, MultipartFile brandImg) throws Exception {

        //이미지 이름.
        String fileCode = fileService.uploadRepImg(brandImg);
        brand.setImg(fileCode);

        return brandDao.insert(brand);
    }

    public int updateBrand(BrandUpdateDto brandUpdateDto, MultipartFile brandImg)throws Exception{

        //기존 이미지 이름 가져오기.
        //기존 이미지 삭제
        //기존 이미지 이름으로 신규 이미지 로컬 저장 --UUID로 다시 만들 필요 없게.
        // 브랜드 이미지를 여러개로 할 경우 필요할듯.

        return brandDao.update(brandUpdateDto);
    }

    /*
     *       페이지 기반 상품 페이징
     *
     * */
    @Transactional(readOnly = true)
    public List<ProductPageDto> findBrandPageList(PageInfo pageInfo) throws Exception {

        HashMap<String ,Object> pageMap = PageInfo.toHashMap(pageInfo);
        pageInfo.productCountCal(productDao.countProduct(pageMap));
        return productDao.findPageList(pageMap);
    }


}
