package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.product.dao.ProductDao;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.product.dto.pagination.ProductPageDto;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    @Test
    @Order(1)
    @DisplayName("서비스로 상품 등록")
    public void register() throws Exception {

        MultipartFile repImg = createRepImg();
        List<String> sizes = new ArrayList<>();
        sizes.add("L");
        sizes.add("M");

        List<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("red");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);
        quantitis.add(134);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "나이키의 반팔 티는 좋은 제품이다.");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("ALLREUES")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager9")
                .name("모두 다 재사용하는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        String result =productService.registerSave(productRegisterDto, repImg);

        assertNotNull(result);
        assertTrue(result.equals("모두 다 재사용하는 테스트용 의류"));
    }


    /*
    * 상세설명, 설명 이미지, 사진 이미지, 상품 등록
    *
    *
    * */
    @Test
    @Order(2)
    @DisplayName("서비스로 상품 등록2")
    void register2() throws Exception {

        MultipartFile repImg = createRepImg();

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("red");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(332);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("asdfasdf","상세 설명은 짧고 간단하게.");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("CREATE_DESCRIPTION")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C08")
                .managerName("manager10")
                .name("상세 설명 만드는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        List<MultipartFile> desImages = create2Imgs(2,"des");
        List<MultipartFile> repImages = create2Imgs(2,"rep");

        long curLong = System.currentTimeMillis();

        String result =productService.registerSave(productRegisterDto, repImg, desImages, repImages);

        long endLong = System.currentTimeMillis();

        double seconds = (double) (endLong - curLong) /1000;

        //0.329 - Dao 나눠서 각각 처리.

        //0.313 - 다중쿼리로 한번에 처리
        System.out.println(seconds);

        assertNotNull(result);

        assertTrue(result.equals("상세 설명 만드는 테스트용 의류"));
    }

    @Test
    @Order(3)
    @DisplayName("DetailDto 찾아오기")
    void searchProduct1() throws Exception {

        ProductDetailDto productDetailDto = productService.findProductDetailById("P001");

        System.out.println(productDetailDto);


    }
    //별점 - key : starRate , value : DESC, ASC
    //키워드 - key: keyword , value : "문자열" -> (키워드는 페이지방식으로 ?)
    //날짜순 - key : byDate , value : DESC, ASC
    //판매량순 - key : sales , value : DESC, ASC
    //가격순 -  key : price , value : DESC, ASC
    //카테고리 - key : category , value : category_id
    //가격대 정해서 볼수도 있도록.
    @Test
    @Order(4)
    @DisplayName("cursor페이지")
    void cursorPage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 2);
        map.put("byDate", "DESC");
        map.put("key", "P004");
        productService.findCursorList(map).forEach(System.out::println);
        map.remove("byDate");
        map.put("sales","DESC");
        productService.findCursorList(map).forEach(System.out::println);
    }


    @Test
    @Order(5)
    @DisplayName("page 페이지")
    void pagePage() throws Exception{

        ProductPageDto productPageDto = new ProductPageDto();
        productPageDto.setPageNum(4);
        productPageDto.setCountPerPage(2);
        //현재 나이키
        productPageDto.setBrandId("A00000000002");
        productPageDto.setCategoryId("C17");

        //byDate, starRate, sales, price
        productPageDto.setOrderName("starRate");

        productPageDto.setOrderBy("DESC");


        PageInfo pageInfo = new PageInfo();
        pageInfo.setPaging(productPageDto);

        productService.findPageList(pageInfo).forEach(System.out::println);
        System.out.println(pageInfo);

    }

    @Test
    @Order(6)
    @DisplayName("상품 상세 설명 사져오기")
    void detailPage() throws Exception{
        long curLong = System.currentTimeMillis();
        ProductDetailDto productDetailDto = productService.findProductDetailById("P001");
        long endLong = System.currentTimeMillis();

        double seconds = (double) (endLong - curLong) /1000;
        System.out.println(seconds);

        System.out.println(productDetailDto);
    }







    private MultipartFile createMockMultipartFile(String filename) throws IOException {
        return new MockMultipartFile(
                "file", // 파라미터 이름
                filename, // 원래 파일 이름
                "image/jpeg", // MIME 타입
                new ByteArrayInputStream("file content".getBytes()) // 파일 내용
        );
    }

    private MultipartFile createRepImg() throws IOException {
        MultipartFile repImg = createMockMultipartFile("img1.jpg");

        return repImg;
    }


    private List<MultipartFile> create2Imgs(int n, String diff) throws IOException {

        List<MultipartFile> images = new ArrayList<>();

        for(int i = 0; i < n; i++){
                images.add(createMockMultipartFile("images"+i+diff+".jpg"));
        };

        return images;
    }

}