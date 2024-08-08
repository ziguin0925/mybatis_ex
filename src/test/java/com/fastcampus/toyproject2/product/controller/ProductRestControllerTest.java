package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRestControllerTest {

    @Autowired
    ProductRestController productRestController;
    @Autowired
    private ProductDaoMysql productDaoMysql;
    @Autowired
    private StockDaoMysql stockDaoMysql;
    @Autowired
    private ProductDescriptionImgDaoMysql productDescriptionImgDaoMysql;


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



    //나머지 필요 없는 것들에 null 넣어보기.== 됨.
    //상세 설명 재사용하는 경우 등록 가능하도록 - repimg는 무조건 등록해야함.
    @Test
    @Order(1)
    @DisplayName("브랜드ID, 카테고리ID DB 상에 있고 상세 설명을 재사용하는 경우.")
    void register() throws Exception {

        MultipartFile repImg = createRepImg();
        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "");
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

        ResponseEntity<String> result = productRestController.register(productRegisterDto, repImg, null,null);

        assertEquals(HttpStatus.OK,result.getStatusCode());

        String product = productDaoMysql.findNameById(productRegisterDto.getProductId());

        assertTrue(product!=null);

        StockPk stockPk = new StockPk(productRegisterDto.getProductId(),productRegisterDto.getSize().get(0),productRegisterDto.getSize().get(0) );
        Stock stock =stockDaoMysql.findById(stockPk);

        assertEquals(133, stock.getQuantity());

        System.out.println(product);
    }



    //브랜드 코드가 DB에 없으면 등록 불가
    @Test
    @Order(2)
    @DisplayName("브랜드 코드가 DB에 없는 경우")
    public void notBrandCode() throws Exception {

        MultipartFile repImg = createRepImg();

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("NOTBRAND")
                .brandId("A002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager12")
                .name("브랜드 코드 없는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        ResponseEntity<String> result = productRestController.register(productRegisterDto, repImg, null,null);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        System.out.println("브랜드 없음 :"+result.getBody());
    }



    //카테고리 코드가 DB에 없으면 등록 불가.
    @Test
    @Order(3)
    @DisplayName("카테고리 코드가 DB에 없는 경우")
    public void notCategoryCode() throws Exception {

        MultipartFile repImg = createRepImg();

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("NOTCATEGORY")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C99")
                .managerName("manager11")
                .name("카테고리 없는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        ResponseEntity<String> result = productRestController.register(productRegisterDto, repImg, null,null);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        System.out.println("카테고리 없음 :"+result.getBody());
    }


    //브랜드와 카테고리가 DB에 있고, 상세 설명을 새로 만드려고 하는경우
    //제품 이미지, 설명 이미지 등록
    @Test
    @Order(4)
    @DisplayName("상세 설명 id 가 없는 경우")
    public void notDescriptionCode() throws Exception {

        MultipartFile repImg = createRepImg();

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);
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

        ResponseEntity<String> result = productRestController.register(productRegisterDto, repImg, desImages,repImages);
        System.out.println(result.getStatusCode());

        //상세설명 만들어짐.
        assertEquals(HttpStatus.OK,result.getStatusCode());

        System.out.println("상세 설명 만듦 :"+result.getBody());

        List<ProductDescriptionImg> imgList =productDescriptionImgDaoMysql.findAllByProductDescriptionId(productDescriptionDto.getProductDescriptionId());

        //상세설명과 연관된 이미지가 로컬에 저장되고, DB에도 저장되는지 확인.
        assertTrue(imgList.size()==4);

        imgList.forEach(System.out::println);
    }

}