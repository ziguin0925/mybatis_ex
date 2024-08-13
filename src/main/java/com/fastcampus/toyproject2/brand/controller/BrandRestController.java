package com.fastcampus.toyproject2.brand.controller;


import com.fastcampus.toyproject2.brand.dto.BrandCreateDto;
import com.fastcampus.toyproject2.brand.dto.BrandUpdateDto;
import com.fastcampus.toyproject2.brand.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name ="브랜드 RestController", description = "브랜드 페이지에서 일어나는 것들.")
public class BrandRestController {

    private final BrandService brandService;



    /*
    * 브랜드 등록 처리
    * */
    @Operation(summary = "브랜드 등록", description = "브랜드 등록하는 api")
    @PostMapping("/register")
    public ResponseEntity<?> brandRegister( @Validated @RequestPart(value = "Brand") BrandCreateDto brand
            , @RequestPart(required = true) MultipartFile brandImg ) throws Exception {

        brandService.createBrand(brand, brandImg);


        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brand.getName() + "브랜드 등록 완료"));
    }

    //register와 update의 차이가 별로 없음...

    @Operation(summary = "브랜드 내용 수정", description = "브랜드 등록과 브랜드 수정에서 받는 파라미터의 차이가 거의 없음.\n합칠지 따로 둘지 고민.")
    @PatchMapping("/update")
    public ResponseEntity<?> brandUpdate(@Validated @RequestPart(value = "Brand")BrandUpdateDto brandUpdateDto
            , @RequestPart(required = false) MultipartFile brandImg) throws Exception {

        brandService.updateBrand(brandUpdateDto, brandImg);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brandUpdateDto.getName() + "브랜드 수정 완료"));
    }



}
