package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.Item.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.Item.product.dto.Product;
import com.fastcampus.toyproject2.Item.product.dto.ProductDetailDto;
import com.fastcampus.toyproject2.Item.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.Item.product.dto.ProductUpdateDto;
import com.fastcampus.toyproject2.Item.productDescription.dao.ProductDescriptionDao;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.Item.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.ETC.util.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProductDaoMysqlTest {

    @Autowired
    ProductDaoMysql productDao;

    @Autowired
    StockDaoMysql stockDao;

    @Autowired
    FileService fileService;

    @Value(("${productRepImgLocation}"))
    private String imgRepLocation;


    @Qualifier("productDescriptionDao")
    @Autowired
    private ProductDescriptionDao productDescriptionDao;


    @Test
    @Order(1)
    @DisplayName("Dao Test")
    public void getProductDao() throws Exception {


        assertTrue(productDao.findNameById("P001").equals("나이키 남성 빨강 티셔츠"));
    }


    //기존 상세 설명 Id, 브랜드 Id, 카테고리 Id 사용
    @Test
    @Order(2)
    @DisplayName("상품 생성1")
    public void createProduct1() throws Exception {

        Product product = Product.builder()
                .productId("asdf")
                .price(1234)
                .productDescriptionId("NIKE000000001")
                .name("ALLREUES")
                .repImg("Asdfasdf")
                .brandId("A00000000001")
                .categoryId("C01")
                .registerManager("asdf")
                .build();

        productDao.insertTest(product);

        assertTrue(product.getName().equals(productDao.findNameById(product.getProductId())));






    }

    @Test
    @Order(3)
    @DisplayName("상품 id로 삭제")
    void deleteProduct1() throws Exception {


        Product product = Product.builder()
                .productId("asdf")
                .price(1234)
                .productDescriptionId("NIKE000000001")
                .name("ALLREUES")
                .repImg("Asdfasdf")
                .brandId("A00000000001")
                .categoryId("C01")
                .registerManager("asdf")
                .build();

        productDao.insertTest(product);

        assertTrue(product.getName().equals(productDao.findNameById(product.getProductId())));



        int i =productDao.deleteByProductId(product.getProductId());
        System.out.println(i);
        String deletename =productDao.findNameById("ALLREUES");
        assertEquals(null,deletename);

    }

    @Test
    @Order(4)
    @DisplayName("productId로 DetailDto 찾아오기")
    void searchProduct1() throws Exception {

        Product product = Product.builder()
                .productId("asdf")
                .price(1234)
                .productDescriptionId("NIKE000000001")
                .name("ALLREUES")
                .repImg("Asdfasdf")
                .brandId("A00000000001")
                .categoryId("C01")
                .registerManager("asdf")
                .build();

        productDao.insertTest(product);

        ProductDetailDto productDetailDto = productDao.findProductDetailById(product.getProductId());

        assertTrue(productDetailDto.getProductId().equals(product.getProductId()));
        assertTrue(productDetailDto.getName().equals(product.getName()));

        System.out.println(productDetailDto);

    }

    @Test
    @Order(5)
    @DisplayName("제품 수정")
    void updateProduct1() throws Exception {

        Product product = Product.builder()
                .productId("asdf")
                .price(1234)
                .productDescriptionId("NIKE000000001")
                .name("ALLREUES")
                .repImg("Asdfasdf")
                .brandId("A00000000001")
                .categoryId("C01")
                .registerManager("asdf")
                .build();

        productDao.insertTest(product);

        ProductUpdateDto productUpdateDto1 = new ProductUpdateDto();

        productUpdateDto1.setProductId(product.getProductId());
        productUpdateDto1.setName("상품 이름 수정");

        productDao.updateProduct(productUpdateDto1);
        assertTrue(productDao.findNameById(product.getProductId()).equals("상품 이름 수정"));

        ProductUpdateDto productUpdateDto2 = new ProductUpdateDto();
        productUpdateDto2.setProductId(product.getProductId());
        productUpdateDto2.setPrice(1423);
        productUpdateDto2.setName("상품 가격, 이름, 카테고리 수정");
        productDao.updateProduct(productUpdateDto2);
        assertTrue(productDao.findNameById(product.getProductId()).equals("상품 가격, 이름, 카테고리 수정"));


        String deletename =productDao.findNameById(product.getProductId());
    }

//    @Test
//    @Order(6)
//    @DisplayName("제품 수정 id 없을때/")




    //제품 생성
    private void createProduct(ProductRegisterDto productRegisterDto) throws Exception {

        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        MultipartFile repFile = createRepImg();

        String repFileCode = fileService.uploadFile(imgRepLocation, repFile);

        Product registerProduct = ProductRegisterDto.toProduct(productRegisterDto, repFileCode);

        productDao.insertTest(registerProduct);
    }




    private MultipartFile createRepImg() throws IOException {
        MultipartFile repImg = createMockMultipartFile("img1.jpg");

        return repImg;
    }

    private MultipartFile createMockMultipartFile(String filename) throws IOException {
        return new MockMultipartFile(
                "file", // 파라미터 이름
                filename, // 원래 파일 이름
                "image/jpeg", // MIME 타입
                new ByteArrayInputStream("file content".getBytes()) // 파일 내용
        );
    }

}