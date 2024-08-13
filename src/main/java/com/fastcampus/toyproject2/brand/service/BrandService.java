package com.fastcampus.toyproject2.brand.service;


import com.fastcampus.toyproject2.brand.dao.BrandDaoMysql;
import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import com.fastcampus.toyproject2.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandDaoMysql brandDao;
    private final FileService fileService;

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


}
