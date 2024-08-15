package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.brand.service.BrandService;
import com.fastcampus.toyproject2.category.service.CategoryService;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.product.dto.ProductUpdateDto;
import com.fastcampus.toyproject2.product.service.ProductService;
import com.fastcampus.toyproject2.productDescription.service.ProductDescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name ="Product API", description ="상품 관련 API")
public class ProductRestController {
    private final ProductService productService;

    private final BrandService brandService;

    private final CategoryService categoryService;

    private final ProductDescriptionService productDescriptionService;

    @ExceptionHandler(AssertionError.class)
    public ResponseEntity<?> IllegalArgumentException(AssertionError exception){



        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",exception.getMessage()+"오류발생"));
    }

    /*
    *   상품 등록
    *   ProductRegisterDto
    *   상품 표시 상태 - boolean으로 할건지, enumtype으로 할건지 생각.
    *
    *   저장 할때마자 findBy로 DB에 잘 저장 되었는지 확인이 필요한가?
    *    throws Exception이 있는데
    * */
    @Operation(summary = "상품 등록", description = "상품 등록 Dto, 상품 대표 이미지, 상품 설명 이미지 , 상품 이미지를 매개변수로 받아온다.")
    @PostMapping(value = {"/admin/{productId}"})
    public ResponseEntity<?> register(@Valid @RequestPart(value = "ProductRegisterDto") ProductRegisterDto productRegisterDto
            , @RequestPart(value = "RepImg", required = true) MultipartFile repImg
            , @RequestPart(value = "DescriptionImgs", required = false) List<MultipartFile> desImgs
            , @RequestPart(value = "RepresentationImgs", required = false) List<MultipartFile> represenImgs
            /*, Model model */) {


        //나중에 회원 테이블 구현이 끝나면 회원 조회.

        //이거 여기다가 하는게 맞나.
        if (brandService.findById(productRegisterDto.getBrandId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",productRegisterDto.getName() + " 불가(brand 없음)"));

        }

        if (categoryService.findById(productRegisterDto.getCategoryId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",productRegisterDto.getName() + " 불가(카테고리 없음)"));
        }


        String product = "";


        //사진 저장했는데 브랜드나 카테고리를 제대로 안받아 온거면 안됨, 맨 마지막.
        //product_description_id 가 있는지 확인 - 없으면 product_description과 product_description_img 둘다 생성해야함.


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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }

    }


    /*
    *   상품 수정
    *   이름, 대표 이미지, 카테고리, 가격, 화면 표시유무, 등록자(register_manager), 상세 설명 기존에 있는거로 변경.
    *   상세 설명 새로 생성은 productDescription에서 처리하기 .
    *
    *   상품 수정페이지에서 수정시에
    *   1.
    *       상품 수정시에 프론트에서 기존 내용과 바뀐게 있으면 바뀐 내용 Dto에 넣어주고, 기존내용과 바뀐게 없으면 null로 넣어주기. - 전체 내용에 대한 request 버튼을 두기
    *   2.
    *       하나씩만 변경 request가 가도록 - 하나씩 request 버튼을 두기.
    * */
    @Operation(summary="상품 수정", description = "상품을 수정할 때 상세 설명, 상세 설명 이미지, 상품 이미지들을 수정할 때에는 다른 Controller에서 처리할 수 있도록 다른 페이지로 넘어가도록 처리.")
    @PatchMapping(value ={"/admin/{productId}"})
    public ResponseEntity<?> productupdate(@PathVariable(name = "productId") String productId
            , @Validated @RequestPart(value = "ProductUpdateDto") ProductUpdateDto productUpdateDto)  {
                //해당 상품이 존재하는지 확인?

                try {
                        productService.updateProduct(productUpdateDto);
                }catch ( NotFoundException e ) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
                }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",productId + "상품 수정 완료"));
    }




    @Operation(summary="상품 삭제", description = "productId를 통한 상품 삭제")
    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<?> productdelete(@PathVariable("productId") String productId)  {
        try {
            productService.deleteProduct(productId);
        }catch (NotFoundException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message",e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",productId+ "상품 삭제 완료"));
    }



    @Operation(summary = "상품 리스트", description ="상품 리스트 커서기반 불러오기 \n\n" +
            "cursor(cursor), sortCode(정렬 기준), category(where절), keyword(where절), size 반환해 줘야 다음 게시물 가져다줌.")
    @GetMapping(value ="/list")
    public ResponseEntity<?> productCursorList(@RequestParam HashMap<String, Object> cursorMap) throws Exception {
        //cursorMap.get("key")가 null 이면 mapper에서 정렬된 데이터의 맨 첫번째부터 데이터 가져옴.
        //GetMapping 은 RequestBody 사용 불가? - 사용가능 하다하는 곳도 있고 못하다 하는 곳도 있고...
        // http 에서 Get으로 body 전송이 안좋다는 얘기도...

        List cursorList = productService.findCursorList(cursorMap);

        //List안에 내용이 null이면 더이상 안보여 주도록 프론트에서 설정하기.
        return ResponseEntity.status(HttpStatus.OK).body(cursorList);
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

