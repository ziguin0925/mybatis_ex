package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.brand.service.BrandService;
import com.fastcampus.toyproject2.category.service.CategoryService;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.product.dto.ProductUpdateDto;
import com.fastcampus.toyproject2.product.service.ProductService;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescription.service.ProductDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    private final BrandService brandService;

    private final CategoryService categoryService;

    private final ProductDescriptionService productDescriptionService;


    /*
    *   상품 등록
    *   ProductRegisterDto
    *   상품 표시 상태 - boolean으로 할건지, enumtype으로 할건지 생각.
    *
    *   저장 할때마자 findBy로 DB에 잘 저장 되었는지 확인이 필요한가?
    *    throws Exception이 있는데
    * */
    @PostMapping(value = {"/register"})
    public ResponseEntity<?> register(@Validated @RequestPart(value = "ProductRegisterDto") ProductRegisterDto productRegisterDto
            , @RequestPart(value = "RepImg", required = true) MultipartFile repImg
            , @RequestPart(value = "DescriptionImgs", required = false) List<MultipartFile> desImgs
            , @RequestPart(value = "RepresentationImgs", required = false) List<MultipartFile> represenImgs
            /*, Model model */) throws Exception {


        //나중에 회원 테이블 구현이 끝나면 회원 조회.


        if (brandService.findById(productRegisterDto.getBrandId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",productRegisterDto.getName() + " 불가(brand 없음)"));

        }

        if (categoryService.findById(productRegisterDto.getCategoryId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",productRegisterDto.getName() + " 불가(카테고리 없음)"));
        }


        String product = "";


        //사진 저장했는데 브랜드나 카테고리를 제대로 안받아 온거면 안됨, 맨 마지막.
        //product_description_id 가 있는지 확인 - 없으면 product_description과 product_description_img 둘다 생성해야함.
        ProductDescriptionDto productDescription = productDescriptionService.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());

        if (productDescription != null) {
            try {
                product = productService.registerSave(productRegisterDto, repImg);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",product + " 완료"));

            }catch (Exception e) {
                //현재 서버가 일시적으로 사용이 불가함
                //일반적으로 유지보수로 인해 중단되거나 과부하가 걸린 서버임
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("message",productRegisterDto.getName() + " 불가(등록 에러)"));
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
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",product + " 완료"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("message",productRegisterDto.getName() + " 불가(상세 설명 항목 등록 에러)"));
        }

    }


    /*
    *   상품 수정
    *   이름, 대표 이미지, 카테고리, 가격, 화면 표시유무, 등록자(register_manager), 상세 설명 기존에 있는거로 변경.
    *   상세 설명 새로 생성은 productDescription에서 처리하기 .
    * */
    @PatchMapping(value ={"/update"})
    public ResponseEntity<?> productupdate(@Validated @RequestPart(value = "ProductUpdateDto") ProductUpdateDto productUpdateDto){

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",productUpdateDto.getName() + "브랜드 수정 완료"));
    }


    /*
    *       상품 좋아요.
    *       회원 테이블과 찜 테이블 조인 해서 회원이 자신이 누른 좋아요 찾을 수 있게
    *       찜 누르면 찜 테이블에 찜 갯수 +1
    *       or
    *        join해서 카운트 한 다음 찜 갯수 가져올지
    *
    * */


    /*
    *       후기 작성
    *       후기 작성하게 되면 해당 상품의 후기 글에
    *       후기 에는 별점 작성하도록 생성.
    *       후기 작성하면 상품의 테이블에 있는 후기 카운트 +1
    *       + 상품의 별점 평균 내기.
    * */


}

