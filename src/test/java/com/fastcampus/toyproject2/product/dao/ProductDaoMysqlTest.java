package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductDetailDto;
import com.fastcampus.toyproject2.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.product.dto.ProductUpdateDto;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDao;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.stock.dto.StockPk;
import com.fastcampus.toyproject2.util.FileService;
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
import java.util.ArrayList;
import java.util.List;

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
    public void getProductDao() {

        try {
            System.out.println(productDao.findNameById("P001"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //기존 상세 설명 Id, 브랜드 Id, 카테고리 Id 사용
    @Test
    @Order(2)
    @DisplayName("상품 생성1")
    public void createProduct1() throws Exception {

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "");


        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //재고는 생성 안함.
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

        createProduct(productRegisterDto);

        assertTrue(productRegisterDto.getName().equals(productDao.findNameById("ALLREUES")));






    }

    @Test
    @Order(3)
    @DisplayName("상품 id로 삭제")
    void deleteProduct1() throws Exception {

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "");


        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //재고는 생성 안함.
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

        createProduct(productRegisterDto);

        assertTrue(productRegisterDto.getName().equals(productDao.findNameById("ALLREUES")));




        int i =productDao.deleteByProductId(productRegisterDto.getProductId());
        System.out.println(i);
//        assertTrue(i==1);
        String deletename =productDao.findNameById("ALLREUES");

        assertEquals(null,deletename);
        System.out.println(stockDao.findByStockPk(StockPk.IdAndSizeAndColor("ALLREUES","L","blue")));
        assertTrue(stockDao.findByStockPk(StockPk.IdAndSizeAndColor("ALLREUES","L","blue")).size()==0);




    }

    @Test
    @Order(4)
    @DisplayName("productId로 DetailDto 찾아오기")
    void searchProduct1() throws Exception {

        ProductDetailDto productDetailDto = productDao.findProductDetailById("P001");

        System.out.println(productDetailDto);

    }

    @Test
    @Order(5)
    @DisplayName("제품 수정")
    void updateProduct1() throws Exception {

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "");


        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //재고는 생성 안함.
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("CREATE_DESCRIPTION")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager9")
                .name("예시")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        createProduct(productRegisterDto);

        ProductUpdateDto productUpdateDto1 = new ProductUpdateDto();

        productUpdateDto1.setProductId("CREATE_DESCRIPTION");
        productUpdateDto1.setName("상품 이름 수정");

        productDao.updateProduct(productUpdateDto1);
        assertTrue(productDao.findNameById("CREATE_DESCRIPTION").equals("상품 이름 수정"));

        ProductUpdateDto productUpdateDto2 = new ProductUpdateDto();
        productUpdateDto2.setProductId("CREATE_DESCRIPTION");
        productUpdateDto2.setPrice(1423);
        productUpdateDto2.setName("상품 가격, 이름, 카테고리 수정");
        productDao.updateProduct(productUpdateDto2);
        assertTrue(productDao.findNameById("CREATE_DESCRIPTION").equals("상품 가격, 이름, 카테고리 수정"));


        String deletename =productDao.findNameById("CREATE_DESCRIPTION");
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