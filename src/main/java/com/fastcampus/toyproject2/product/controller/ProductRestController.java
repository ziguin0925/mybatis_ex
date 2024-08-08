package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.brand.dto.Brand;
import com.fastcampus.toyproject2.brand.service.BrandService;
import com.fastcampus.toyproject2.category.service.CategoryService;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.product.service.ProductService;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.service.ProductDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    private final BrandService brandService;

    private final CategoryService categoryService;

    private final ProductDescriptionService productDescriptionService;


    @PostMapping(value = {"/register"})
    public ResponseEntity<String> register(@RequestPart(value = "ProductRegisterDto") ProductRegisterDto productRegisterDto
            , @RequestPart(value = "RepImg") MultipartFile repImg
            , @RequestPart(value = "DescriptionImgs", required = false) MultipartFile[] desImgs
            , @RequestPart(value = "RepresentationImgs", required = false) MultipartFile[] represenImgs
            /*, Model model */) throws Exception {

        /*
         * product 필드
         * 상품 코드, 상품 이름 , 상품 대표 이미지, 상품 가격, 상품 화면 표시, 상품 등록 일시, 상품 변경일시, 판매량, 상품 관리자, 별점, 상품 상세 설명 id, 브랜드 코드, 카테고리 코드
         *
         * 받아올 값
         * 상품 코드, 상품 이름, 상품 대표 이미지, 상품 가격, 상품 관리자, 카테고리 코드, 브랜드 코드, 상품 상세 설명 id.
         * 상품 상세 설명 이미지, 상품 이미지.
         *
         *
         * 상품 상세 설명 id, 브랜드 코드, 카테고리 코드 미리 생성되어 있어야함.
         *
         *
         * brand, category description id 만  객체 전부 다 받아올지 생각.
         * 상품만 저장하는거면 id 만 받아와도 상관 없을듯.
         * */


        //받아온 brand 가 있는지 확인. - 없으면 등록 불가.
        Brand brand = brandService.findById(productRegisterDto.getBrandId());
        if (brand == null) {
            return new ResponseEntity<>(productRegisterDto.getName() + " 불가(brand없음)", HttpStatus.BAD_REQUEST);
        }




        //받아온 category 가 있는지 확인. - 없으면 등록 불가.
        String category = categoryService.findById(productRegisterDto.getCategoryId());
        if (category == null) {
            return new ResponseEntity<>(productRegisterDto.getName() + " 불가(카테고리 없음)", HttpStatus.BAD_REQUEST);
        }

        //repImg, desImgs, represenImgs도 있는지 없는지 확인해봐야함.

        String product = "";


        //사진 저장했는데 브랜드나 카테고리를 제대로 안받아 온거면 안됨, 맨 마지막.
        //product_description_id 가 있는지 확인 - 없으면 product_description과 product_description_img 둘다 생성해야함.
        ProductDescription productDescription = productDescriptionService.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());

        if (productDescription != null) {
            try {
                product = productService.registerSave(productRegisterDto, repImg);

            }catch (Exception e) {
                //현재 서버가 일시적으로 사용이 불가함
                //일반적으로 유지보수로 인해 중단되거나 과부하가 걸린 서버임
                return new ResponseEntity<>(productRegisterDto.getName() + " 불가(등록 에러)", HttpStatus.SERVICE_UNAVAILABLE);
            }

        }
        /*
                상세 설명 만들기.
                변수를 한번에 담기.
                이미지 저장, 상품 저장, 재고 저장, 상세 설명 저장.
                repImg, desImgs, represenImgs 세개가 모두 null 이 아니어야함.
            */

        try {
            product = productService.registerSave(productRegisterDto, repImg, desImgs, represenImgs);
            return new ResponseEntity<>(product + " 완료", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(productRegisterDto.getName() + " 불가(등록 에러)", HttpStatus.SERVICE_UNAVAILABLE);
        }

        //나중에 회원 테이블 구현이 끝나면 회원 조회.
    }
}
