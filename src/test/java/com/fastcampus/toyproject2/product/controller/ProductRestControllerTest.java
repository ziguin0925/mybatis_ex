package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.Item.product.controller.ProductRestController;
import com.fastcampus.toyproject2.Item.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.Item.product.dto.ProductRegisterDto;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import com.fastcampus.toyproject2.Item.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.Item.stock.dto.Stock;
import com.fastcampus.toyproject2.Item.stock.dto.StockPk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductRestControllerTest {

    @Autowired
    ProductRestController productRestController;
    @Autowired
    private ProductDaoMysql productDaoMysql;
    @Autowired
    private StockDaoMysql stockDaoMysql;
    @Autowired
    private ProductDescriptionImgDaoMysql productDescriptionImgDaoMysql;

    @Autowired
    private MockMvc mockMvc;


    private MockMultipartFile createMockMultipartFile(String filename, String parameterName) throws IOException {
        return new MockMultipartFile(
                parameterName, // 파라미터 이름
                filename+".jpg", // 원래 파일 이름
                "image/jpeg", // MIME 타입
                new ByteArrayInputStream("file content".getBytes()) // 파일 내용
        );
    }

    private MockMultipartFile createRepImg(String parameterName) throws IOException {
        MockMultipartFile repImg = createMockMultipartFile("img1",parameterName);

        return repImg;
    }

    private List<MockMultipartFile> create2Imgs(int n, String diff,String parameterName) throws IOException {

        List<MockMultipartFile> images = new ArrayList<>();

        for(int i = 0; i < n; i++){
            images.add(createMockMultipartFile("images"+i+diff,parameterName));
        };

        return images;
    }

    private MockMultipartFile dtoToMultiPartFile(ProductRegisterDto productRegisterDto) throws JsonProcessingException {

        String productRegisterDtoJson = new ObjectMapper().writeValueAsString(productRegisterDto);

        MockMultipartFile registerDtoContent = new MockMultipartFile(
                "ProductRegisterDto"
                , "productRegisterDto.json"
                , "application/json"
                , productRegisterDtoJson.getBytes(StandardCharsets.UTF_8)
        );

        return registerDtoContent;
    }


    //multipart로 하면 MockHttpServletRequest의 Body가 null로 되는듯.
    //나머지 필요 없는 것들에 null 넣어보기.== 됨.
    //상세 설명 재사용하는 경우 등록 가능하도록 - repimg는 무조건 등록해야함.
    @Test
    @DisplayName("브랜드ID, 카테고리ID DB 상에 있고 상세 설명을 재사용하는 경우.")
    void register() throws Exception {

        //given
        MockMultipartFile repImg = createRepImg("RepImg");

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "나이키의 반팔 티는 좋은 제품이다.");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantity = new ArrayList<>();
        quantity.add(133);

        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("ALLREUES12341234")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager9")
                .name("모두 다 재사용하는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantity)
                .build();


        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
//                        .content(content)
//                        .contentType(MediaType.APPLICATION_JSON)
                                .file(repImg)
                                .file(registerDtoContent)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andDo(print());


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(productRegisterDto.getName() + " 완료"));



        //then
        String product = productDaoMysql.findNameById(productRegisterDto.getProductId());

        //생겼는지 확인.
        assertTrue(product!=null);
        System.out.println(productRegisterDto.getColor());
        System.out.println(productRegisterDto.getSize().get(0));
        System.out.println(productRegisterDto.getQuantity().get(0));

        StockPk stockPk = new StockPk(productRegisterDto.getProductId()
                ,productRegisterDto.getSize().get(0)
                ,productRegisterDto.getColor().get(0)
        );

        //일단 stock 하나만 만들었으니 하나만 체크해보기. 여러개 넣는거 test하기.
        List<Stock> stock =stockDaoMysql.findByStockPk(stockPk);

        assertEquals(133, stock.get(0).getQuantity().intValue());

        System.out.println(product);
    }



    //브랜드 코드가 DB에 없으면 등록 불가
    @Test
    @DisplayName("브랜드 코드가 DB에 없는 경우")
    public void notBrandCode() throws Exception {

        //given

        MockMultipartFile repImg = createRepImg("RepImg");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("NOTBRAND113113113")
                .brandId("A002") //해당 브랜드 ID 없음.
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager12")
                .name("브랜드 코드 없는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);

        //when
        ResultActions resultActions =mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                                .file(repImg)
                                .file(registerDtoContent)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andDo(print());


        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(productRegisterDto.getName() + " 불가(brand 없음)"));

        //진짜 없는지 확인
        String product = productDaoMysql.findNameById(productRegisterDto.getProductId());
        assertNull(product);

    }



    //카테고리 코드가 DB에 없으면 등록 불가.
    @Test
    @DisplayName("카테고리 코드가 DB에 없는 경우")
    public void notCategoryCode() throws Exception {

        //given
        MockMultipartFile repImg = createRepImg("RepImg");

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

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);


        ResultActions resultActions = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                        .file(repImg)
                        .file(registerDtoContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());


        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(productRegisterDto.getName() + " 불가(카테고리 없음)"));


    }


    //브랜드와 카테고리가 DB에 있고, 상세 설명을 새로 만드려고 하는경우
    //제품 이미지, 설명 이미지 등록
    @Test
    @DisplayName("상세 설명도 새로 만들기 (상세 설명 id가 없는 경우)")
    public void notDescriptionCode() throws Exception {

        //given
        MockMultipartFile repImg = createRepImg("RepImg");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("asdfasdf", "상세 설명은 짧고 간단하게.");
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

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);

        List<MockMultipartFile> desImages = create2Imgs(2,"des","DescriptionImgs");
        List<MockMultipartFile> repImages = create2Imgs(2,"rep","RepresentationImgs");


        MockMultipartHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.multipart("/product/admin/"+productRegisterDto.getProductId());

        for (MockMultipartFile file : desImages) {
            reqBuilder.file(file);
        }
        for (MockMultipartFile file : repImages) {
            reqBuilder.file(file);
        }


        ResultActions resultActions = mockMvc.perform(reqBuilder
                        .file(registerDtoContent)
                        .file(repImg)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(productRegisterDto.getName() + " 완료"));


        //상세설명 만들어짐.

        List<ProductDescriptionImgDetailDto> imgList =productDescriptionImgDaoMysql.findAllByProductDescriptionId(productDescriptionDto.getProductDescriptionId());

        //상세설명과 연관된 이미지가 로컬에 저장되고, DB에도 저장되는지 확인.
        assertTrue(imgList.size()==4);

        imgList.forEach(System.out::println);
    }



    @Test
    @DisplayName("재고 여러개 들어가는지 확인")
    void stockList() throws Exception {
        //given
        MockMultipartFile repImg = createRepImg("RepImg");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");
        sizes.add("M");

        List<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("red");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);
        quantitis.add(123);

        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("asdfasdf", "상세 설명은 짧고 간단하게.");
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

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);

        List<MockMultipartFile> desImages = create2Imgs(2,"des","DescriptionImgs");
        List<MockMultipartFile> repImages = create2Imgs(2,"rep","RepresentationImgs");


        MockMultipartHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.multipart("/product/admin/"+productRegisterDto.getProductId());

        for (MockMultipartFile file : desImages) {
            reqBuilder.file(file);
        }
        for (MockMultipartFile file : repImages) {
            reqBuilder.file(file);
        }


        ResultActions resultActions = mockMvc.perform(reqBuilder
                        .file(registerDtoContent)
                        .file(repImg)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(productRegisterDto.getName() + " 완료"));

        List<Stock> stocks = stockDaoMysql.findByStockPk(StockPk.onlyId("CREATE_DESCRIPTION"));


        assertTrue(stocks.size()==colors.size());

        //나중에 stock 컬러랑 size도 list로 다 확인하는 코드 짜기.
        for (Stock stock : stocks) {
            assertTrue(stock.getProductId().equals(productRegisterDto.getProductId()));
        }
        stocks.forEach(System.out::println);
    }


    //null, "", 25자 이상
    //공백 10개 이상은 생각해보기
    @Test
    @DisplayName("상품 ID 검증")
    void testName() throws Exception {
        MockMultipartFile repImg = createRepImg("RepImg");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //25자 이상 검증, "", " "
        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("Over25sdaassdfasdfsdasdasdasdfasd")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C04")
                .managerName("manager11")
                .name("상품 이름 25자 이상")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);


        ResultActions resultActions1 = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                        .file(repImg)
                        .file(registerDtoContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions1
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..message")
                        .value("상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
                );



        //null
        productRegisterDto.setProductId(null);
        MockMultipartFile registerDtoContent2 = dtoToMultiPartFile(productRegisterDto);

        ResultActions resultActions2 = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                        .file(repImg)
                        .file(registerDtoContent2)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions2
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..message")
                        .value("상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
                );
    }


    //대표 사진이 없을때 통과 못함.
    //통과는 못하지만 400상태와 메세지를 다르게 바꿀 방법을 찾기
    @Test
    @DisplayName("상품 상세설명 이미지 유효성")
    void testrepImg() throws Exception {


        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //25자 이상 검증, "", " "
        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("Over25ffddffd")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C04")
                .managerName("manager11")
                .name("상품 이름 25자 이상")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);


        ResultActions resultActions1 = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                        .file(registerDtoContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions1
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.message")
//                        .value("상품 ID는 10글자 이상, 25자 이하 이어야합니다. 입력된 값: "+productRegisterDto.getProductId())
//                );

    }

    //1. 상품 이름, 상품 ID 둘다 유효하지 않을 떄.
    @Test
    @DisplayName("2개 유효성")
    void test() throws Exception {
        MockMultipartFile repImg = createRepImg("RepImg");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("blue");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);

        //25자 이상 검증, "", " "
        //상품 ID를 서버에서 정할지 유저가 보내주게 할지. - 유저가 보내준다면 중복 상품 id가 있는지 확인 하는 로직 짜기.
        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001","");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("Over25sdaassdfasdfsdasdasdasdfasd")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C04")
                .managerName("manager11")
                .name("상품")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .build();

        MockMultipartFile registerDtoContent = dtoToMultiPartFile(productRegisterDto);


        ResultActions resultActions1 = mockMvc.perform(multipart("/product/admin/"+productRegisterDto.getProductId())
                        .file(repImg)
                        .file(registerDtoContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andDo(print());

        resultActions1
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$..exception[0].message")
//                        .value("상품 ID는 10글자 이상, 25자 이하 이어야합니다.")
//                        Expected :상품 이름은 3글자 이상, 25자 미만 이어야 합니다.
//                          Actual   :상품 ID는 10글자 이상, 25자 이하 이어야합니다.
                        //@Valid 2개 이상 안맞을 시 리턴 순서는 스프링 마음대로인듯.


    }


}