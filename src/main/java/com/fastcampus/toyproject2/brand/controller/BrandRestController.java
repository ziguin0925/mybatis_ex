package com.fastcampus.toyproject2.brand.controller;


import com.fastcampus.toyproject2.brand.dto.Brand;
import com.fastcampus.toyproject2.brand.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandRestController {



    /*
    * 브랜드 등록 처리
    * */
    @PostMapping("/register")
    public ResponseEntity<?> brandRegister(@Validated @RequestPart(value = "Brand") Brand brand) {

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brand.getName() + "브랜드 등록 완료"));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> brandUpdate(@Validated @RequestPart(value = "Brand") Brand brand) {

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",brand.getName() + "브랜드 수정 완료"));
    }
}
