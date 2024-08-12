package com.fastcampus.toyproject2.brand.controller;


import com.fastcampus.toyproject2.brand.dto.BrandDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import com.fastcampus.toyproject2.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {

    private final BrandService brandService;



    /*
    * 브랜드 등록 처리
    * */
    @PostMapping("/register")
    public ResponseEntity<?> brandRegister(@Validated @RequestPart(value = "Brand") BrandDto brand
            , @RequestPart(required = true) MultipartFile brandImg ) throws Exception {

        brandService.createBrand(brand, brandImg);


        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brand.getName() + "브랜드 등록 완료"));
    }

    //register와 update의 차이가 별로 없음...

    @PatchMapping("/update")
    public ResponseEntity<?> brandUpdate(@Validated @RequestPart(value = "Brand")BrandUpdateDto brandUpdateDto
            , @RequestPart(required = false) MultipartFile brandImg) throws Exception {

        brandService.updateBrand(brandUpdateDto, brandImg);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brandUpdateDto.getName() + "브랜드 수정 완료"));
    }



}
