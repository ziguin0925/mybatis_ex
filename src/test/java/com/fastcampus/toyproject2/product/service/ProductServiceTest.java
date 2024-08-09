package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    @DisplayName("서비스로 상품 등록")
    public void register() throws Exception {

        MultipartFile repImg = createRepImg();
        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

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

        MultipartFile[] desImages = create2Imgs("des");
        MultipartFile[] repImages = create2Imgs("rep");

        String result =productService.registerSave(productRegisterDto, repImg, desImages, repImages);

        assertNotNull(result);

        assertTrue(result.equals("상세 설명 만드는 테스트용 의류"));

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


    private MultipartFile[] create2Imgs(String diff) throws IOException {
        MultipartFile[] productImgs = new MultipartFile[]{
                createMockMultipartFile("images1"+diff+".jpg"),
                createMockMultipartFile("images2"+diff+".jpg"),

        };
        return productImgs;
    }

}