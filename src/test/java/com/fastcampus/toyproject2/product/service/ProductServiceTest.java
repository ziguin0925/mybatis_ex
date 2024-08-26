package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass.DuplicateProductDescriptionIdException;
import com.fastcampus.toyproject2.product.dao.ProductDao;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.product.dto.pagination.ProductRequestPageDto;
import com.fastcampus.toyproject2.product.dto.pagination.cursor.ProductCursorPageDto;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDao;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ImagePathDto;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgRegisterDto;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDescriptionDaoMysql productDescriptionDao;


    @Test
    @DisplayName("서비스로 상품 등록 - 클라이언트가 상세 설명을 재사용하는 경우")
    public void register() throws Exception {

        List<String> sizes = new ArrayList<>();
        sizes.add("L");
        sizes.add("M");

        List<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("red");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(133);
        quantitis.add(134);

        List<ProductDescriptionImgRegisterDto> descriptionImgs = new ArrayList<>();
        descriptionImgs.add(new ProductDescriptionImgRegisterDto("상세설명 이미지.png",35000L,"image/png"));


        List<ProductDescriptionImgRegisterDto> productImgs=new ArrayList<>();
        productImgs.add(new ProductDescriptionImgRegisterDto("상품 이미지.png",35000L,"image/png"));




        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "나이키의 반팔 티는 좋은 제품이다.");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("qwreqwetqwer")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager9")
                .name("모두 다 재사용하는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .desImgs(descriptionImgs)
                .represenImgs(productImgs)
                .repImg("대표 이미지")
                .build();



        ImagePathDto product = productService.registerSave(productRegisterDto);

        assertNotNull(product);
        assertTrue(productService.findProductDetailById(productRegisterDto.getProductId()).getName().equals(productRegisterDto.getName()));
        ProductDescriptionDto productDescriptionDtoDB  =productDescriptionDao.findById(productDescriptionDto.getProductDescriptionId());
        assertTrue(productDescriptionDtoDB.getDescription().equals(productDescriptionDto.getDescription()));

    }


    /*
    * 상세설명, 설명 이미지, 사진 이미지, 상품 등록
    *
    *
    * */
    @Test
    @DisplayName("서비스로 상품 등록2 - 클라이언트가 상세 설명을 직접 생성할 경우")
    void register2() throws Exception {
        //자세한거는 controllerTest에서 처리하기.
        //상품의 product_id 는 무조건 전부 같은 길이의 문자열로 두기. - mysql에서 문자열의 비교 방식 때문(한자리 문자씩 비교함).

        MultipartFile repImg = createRepImg();

        List<String> sizes = new ArrayList<>();
        sizes.add("L");

        List<String> colors = new ArrayList<>();
        colors.add("red");

        List<Integer> quantitis = new ArrayList<>();
        quantitis.add(332);

        List<ProductDescriptionImgRegisterDto> descriptionImgs = new ArrayList<>();
        descriptionImgs.add(new ProductDescriptionImgRegisterDto("상세설명 이미지.png",35000L,"image/png"));


        List<ProductDescriptionImgRegisterDto> productImgs=new ArrayList<>();
        productImgs.add(new ProductDescriptionImgRegisterDto("상품 이미지.png",35000L,"image/png"));


        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("ADIDAS0000001","상세 설명은 짧고 간단하게.");
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
                .represenImgs(productImgs)
                .desImgs(descriptionImgs)
                .repImg("대표 이미지")
                .build();


        long curLong = System.currentTimeMillis();
        try {
            productService.registerSave(productRegisterDto);
        }catch (Exception e){
            assertTrue((e instanceof DuplicateProductDescriptionIdException));
        }

        long endLong = System.currentTimeMillis();

        double seconds = (double) (endLong - curLong) /1000;

        //0.329 - Dao 나눠서 각각 처리.

        //0.313 - 다중쿼리로 한번에 처리
        System.out.println(seconds);

//        assertTrue(productService.findProductDetailById("CREATE_DESCRIPTION").getName().equals(productRegisterDto.getName()));
        }

    @Test
    @DisplayName("DetailDto 찾아오기")
    void searchProduct1() throws Exception {
        String requestProductId = "P004";


        //null일 경우 controller 예외 처리 해주기. -> 잘못된 접근
        ProductDetailDto productDetailDto  = productService.findProductDetailById(requestProductId);

        //다음에는 상품을 등록하고 DetailDto 찾아오기. - 더 세세하게 test할 수 있도록.

        System.out.println(productDetailDto);

        assertTrue(productDetailDto.getProductId().equals(requestProductId));



    }

    @Test
    @DisplayName("product_id가 없는경우")
    void searchProductIsNull() throws Exception {
        String requestProductId = "agsdfsadg";//데이터 베이스에서 보고 넣기.

        //null일 경우 controller 예외 처리 해주기. -> 잘못된 접근
        ProductDetailDto productDetailDto = null;
        try{
            productDetailDto = productService.findProductDetailById(requestProductId);
        }catch (NotFoundException e){
            assertTrue(e.getMessage().equals("해당 품목을 찾을 수 없습니다."));
            System.out.println(e.getMessage());
        }

        System.out.println(productDetailDto);

//        assertTrue(productDetailDto.getProductId().equals(requestProductId));

    }



    // orderby 절 여러개 들어올경우 예외처리하기.
    //별점 예외상황 생각해보기
    @Test
    @DisplayName("cursor 상품 페이지 - 별점")
    void cursorRanking() throws Exception {
/*
                다음 페이지 요청시에 해당 상품의 별점이 바뀌어 버린다면?
        * cursor로 넘겨준 값이 4800000000000000P023 인데
        * 도중에 4900000000000000P023 바뀌면?
        * 다음 요청시에 cursor로 where절을 찾기 못할 것 같음. - 확인해보기. ->  클라이언트가 cursor를 가지고 있을때 데이터 베이스에서 cursor를 바꾸면 못찾음.
        *
        * 찾지 못하면 where 절로 P023에대한 별점을 들고와 4900000000000000P023로 바꿔주고 다시 찾기. -> 이렇게 concat, lpad 로 할거면 따로 데이터를 추가 해주고 인덱스도 만들어줘야함

         SELECT p.product_id as productId
             , p.name as productName
             , p.price as price
             , p.rep_img as repImg
             , p.star_rating as starRating
             , b.brand_id as brandId
             , b.name as brandName

        FROM product p
                 INNER JOIN brand b
                            ON p.brand_id = b.brand_id
        <where>
            <if test ="cursorProductId != null">
                ((p.star_rating <![CDATA[ < ]]> (select star_rating from product where product_id = #{cursorProductId}))
                OR (p.star_rating =  (select star_rating from product where product_id =#{cursorProductId}) AND product_id <![CDATA[ < ]]> #{cursorProductId}))
            </if>
            <if test ="keyword != null">
                AND p.name LIKE CONCAT('%', #{keyword},'%')
            </if>
        </where>
        ORDER BY star_rating desc, product_id desc
        limit #{size}

        이렇게 하면 되지 않을까.


        product table의 star_rating 을 int로 둠
        -> mysql 에 float 로 평균 별점을 저장 한다고 할 때  ex)4.8 - P004
        select * from product where star_rating = 4.8 로 P004 이 안찾아짐. -> 부동 소수점

        어짜피 소수점 한자리수 미만의 숫자는 관심 대상이 아닐 듯함.

        list가 null 일 경우 프론트에서 처리하기. -> "상품이 더이상 없음" 이런식으로

 */

        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "RANKING");
        System.out.println("별점 기반");

        //처음 요청
        List<ProductCursorPageDto> list = productService.findCursorList(map);

        //데이터 직접 확인용
        list.forEach(System.out::println);

        while(true) {
            //응답 리스트 중에 마지막 요소의 별점 가져오기
            int comp = list.get(list.size()-1).getStarRating();

            //리스트의 각 요소가 별점 순으로 정렬 되어 왔는지 확인.
            for(int i =0 ; i<list.size()-1; i++){
                assertTrue(list.get(i).getStarRating()>=list.get(i+1).getStarRating());
            }
            //리스트의 마지막 요소의 product_id 저장. -> 다음 요청의 cursor
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());

            //다음 요청.
            list = productService.findCursorList(map);

            //다음 요청의 리스트가 없을 경우
            if(list.isEmpty()) {
                break;
            }
            //이전 요청의 마지막 요소의 별점과 다음 요청의 첫번째 요소의 별점이 정렬되어있는지.
            //다음 요청의 별점이 이전 요청의 별점과 같거나 작아야함.
            assertTrue(comp>=list.get(0).getStarRating());

            list.forEach(System.out::println);

        }
    }

    @Test
    @DisplayName("cursor 상품 페이지 - 키워드 ,별점")
    void cursorRankingKeyword() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "RANKING");
        map.put("keyword", "나이키");
        System.out.println("별점 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getStarRating();

            for(int i =0 ; i<list.size()-1; i++){
                if(list.size()!= (int) map.get("size") ) {
                    assertTrue(list.get(i).getStarRating() >= list.get(i + 1).getStarRating());
                }
                assertTrue(list.get(i).getProductName().contains(map.get("keyword").toString()));
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getStarRating());

            list.forEach(System.out::println);

        }
    }

    @Test
    @DisplayName("cursor 상품 페이지 - 판매량")
    void cursorPageSalesQuantity() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "SALES");
        System.out.println("판매량 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getSalesQuantity();

            for(int i =0 ; i<list.size()-1; i++){
                assertTrue(list.get(i).getSalesQuantity()>=list.get(i+1).getSalesQuantity());
            }
            ProductCursorPageDto dto =list.get(list.size()-1);

            map.put("cursorProductId",dto.getProductId());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getSalesQuantity());


            list.forEach(System.out::println);
        }

    }

    @Test
    @DisplayName("cursor 상품 페이지 - 키워드 ,판매량")
    void cursorPageSalesQuantityKeyword() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "SALES");
        map.put("keyword", "남성");
        System.out.println("판매량 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getSalesQuantity();

            for(int i =0 ; i<list.size()-1; i++){
                if(list.size()!= (int) map.get("size") ) {
                    assertTrue(list.get(i).getSalesQuantity() >= list.get(i + 1).getSalesQuantity());
                }
                assertTrue(list.get(i).getProductName().contains(map.get("keyword").toString()));
            }
            ProductCursorPageDto dto =list.get(list.size()-1);

            map.put("cursorProductId",dto.getProductId());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getSalesQuantity());


            list.forEach(System.out::println);
        }
    }


    @Test
    @DisplayName("cursor 상품 페이지 - 가격 순(비싼 가격순)")
    void cursorPageHighPrice() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "HIGHPRICE");
        System.out.println("가격 높은순 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getPrice();

            for(int i =0 ; i<list.size()-1; i++){
                assertTrue(list.get(i).getPrice()>=list.get(i+1).getPrice());
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getPrice());

            list.forEach(System.out::println);

        }

        //2번쨰 요청의 경우

    }


    @Test
    @DisplayName("cursor 상품 페이지 - 키워드 , 가격 순(비싼 가격순)")
    void cursorPageHighPriceKeyword() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "HIGHPRICE");
        map.put("keyword", "블라우스");
        System.out.println("가격 높은순 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getPrice();

            for(int i =0 ; i<list.size()-1; i++){
                if(list.size()!= (int) map.get("size") ) {
                    assertTrue(list.get(i).getPrice()>=list.get(i+1).getPrice());
                }
                assertTrue(list.get(i).getProductName().contains(map.get("keyword").toString()));
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());
            map.put("cursorPrice",dto.getPrice());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getPrice());
            list.forEach(System.out::println);
        }
    }

    @Test
    @DisplayName("cursor 상품 페이지  - 가격 순(가격 싼 순)")
    void cursorPageLowPrice() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "LOWPRICE");
        System.out.println("가격 싼 순 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getPrice();

            for(int i =0 ; i<list.size()-1; i++){
                assertTrue(list.get(i).getPrice()<=list.get(i+1).getPrice());
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());
            map.put("cursorPrice",dto.getPrice());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp<=list.get(0).getPrice());

            list.forEach(System.out::println);

        }
    }

    @Test
    @DisplayName("cursor 상품 페이지 - 키워드,가격 순(가격 싼 순)")
    void cursorPageLowPriceKeyword() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "LOWPRICE");
        map.put("keyword", "티셔츠");
        System.out.println("가격 높은순 기반");


        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getPrice();

            for(int i =0 ; i<list.size()-1; i++){
                if(list.size()!= (int) map.get("size") ) {
                    assertTrue(list.get(i).getPrice()<=list.get(i+1).getPrice());
                }
                assertTrue(list.get(i).getProductName().contains(map.get("keyword").toString()));
            }

            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());
            map.put("cursorPrice",dto.getPrice());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp<=list.get(0).getPrice());

            list.forEach(System.out::println);

        }

    }

    // 생성 날짜도 같이 넣을까.
/*
    @Test
    @DisplayName("cursor 상품 페이지  - 최신 순 ")
    void cursorPage5() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "LATEST");
        System.out.println("최신순 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);

        while(true) {
            Long comp = Long.parseLong(list.get(list.size()-1).getCursor().substring(0,14));

            for(int i =0 ; i<list.size()-1; i++){
                assertTrue(list.get(i).get);
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());

            list = productService.findCursorList(map);

            if(list.isEmpty()) {
                break;
            }

            assertTrue(comp>=Long.parseLong(list.get(0).getCursor().substring(0,14)));

            list.forEach(System.out::println);

        }
    }
*/

    @Test
    @DisplayName("삽입 후 테스트")
    void cursorPageInsertAndTest() throws Exception {
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

        List<ProductDescriptionImgRegisterDto> descriptionImgs = new ArrayList<>();
        descriptionImgs.add(new ProductDescriptionImgRegisterDto("상세설명 이미지.png",35000L,"image/png"));


        List<ProductDescriptionImgRegisterDto> productImgs=new ArrayList<>();
        productImgs.add(new ProductDescriptionImgRegisterDto("상품 이미지.png",35000L,"image/png"));




        ProductDescriptionDto productDescriptionDto = new ProductDescriptionDto("NIKE000000001", "나이키의 반팔 티는 좋은 제품이다.");
        ProductRegisterDto productRegisterDto = ProductRegisterDto.builder()
                .price(25900)
                .productId("qwreqwetqwer")
                .brandId("A00000000002")
                .productDescriptionDto(productDescriptionDto)
                .categoryId("C14")
                .managerName("manager9")
                .name("모두 다 재사용하는 테스트용 의류")
                .color(colors)
                .size(sizes)
                .quantity(quantitis)
                .repImg("대표 이미지.jpg")
                .desImgs(descriptionImgs)
                .represenImgs(productImgs)
                .build();

        List<MultipartFile> desImgs = new ArrayList<>();
        List<MultipartFile> represenImgs = new ArrayList<>();

        ImagePathDto product = productService.registerSave(productRegisterDto);

        ProductDetailDto productDetailDto  = productService.findProductDetailById(productRegisterDto.getProductId());

        System.out.println(productDetailDto);

        assertTrue(productDetailDto.getProductId().equals(productRegisterDto.getProductId()));


        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 4);
        map.put("sortCode", "HIGHPRICE");
        map.put("keyword", "블라우스");
        System.out.println("가격 높은순 기반");

        List<ProductCursorPageDto> list = productService.findCursorList(map);
        System.out.println("처음 확인");
        list.forEach(System.out::println);
        while(true) {
            int comp = list.get(list.size()-1).getPrice();

            for(int i =0 ; i<list.size()-1; i++){
                if(list.size()!= (int) map.get("size") ) {
                    assertTrue(list.get(i).getPrice()>=list.get(i+1).getPrice());
                }
                assertTrue(list.get(i).getProductName().contains(map.get("keyword").toString()));
            }
            ProductCursorPageDto dto =list.get(list.size()-1);
            map.put("cursorProductId",dto.getProductId());
            map.put("cursorPrice",dto.getPrice());

            list = productService.findCursorList(map);
            if(list.isEmpty()) {
                break;
            }
            assertTrue(comp>=list.get(0).getPrice());
            list.forEach(System.out::println);
        }
    }

    @Test
    @DisplayName("page 브랜드 상품 페이지 - 기본")
    void pagePage() throws Exception {
        ProductRequestPageDto productRequestPageDto = new ProductRequestPageDto();
        //요청 페이지 4
        productRequestPageDto.setPageNum(4);
        //한 장당 페이지
        productRequestPageDto.setCountPerPage(2);
        //현재 나이키
        productRequestPageDto.setBrandId("A00000000002");



        //무신사 - data-sort-code = LOW_PRICE(낮은 가격순), REVIEW(리뷰 순), NEW(새로운 순) ...
        productRequestPageDto.setSortCode("LOW_PRICE");


        PageInfo pageInfo = new PageInfo();
        pageInfo.setPaging(productRequestPageDto);

        productService.findPageList(pageInfo).forEach(System.out::println);
        System.out.println(pageInfo);

        assertTrue(pageInfo.getEndPage() ==5);
        assertTrue(pageInfo.isNext()==true);

    }

    // orderby 절 여러개 들어올경우 예외처리하기.
    @Test
    @DisplayName("page 브랜드 상품 페이지 - 카테고리 지정")
    void pagePageCategory() throws Exception{

        ProductRequestPageDto productRequestPageDto = new ProductRequestPageDto();
        productRequestPageDto.setPageNum(4);
        productRequestPageDto.setCountPerPage(2);
        //현재 나이키
        productRequestPageDto.setBrandId("A00000000002");

        //카테고리는 반팔
        productRequestPageDto.setCategoryId("C17");


        //무신사 - data-sort-code = LOW_PRICE(낮은 가격순), REVIEW(리뷰 순), NEW(새로운 순) ...
        productRequestPageDto.setSortCode("RANKING");


        PageInfo pageInfo = new PageInfo();
        pageInfo.setPaging(productRequestPageDto);

        productService.findPageList(pageInfo).forEach(System.out::println);
        System.out.println(pageInfo);

    }

    @Test
    @DisplayName("상품 상세 설명 가져오기")
    void detailPage() throws Exception{
        long curLong = System.currentTimeMillis();
        ProductDetailDto productDetailDto = productService.findProductDetailById("P004");
        long endLong = System.currentTimeMillis();

        double seconds = (double) (endLong - curLong) /1000;
        System.out.println(seconds);

        System.out.println(productDetailDto);

        assertTrue(productDetailDto.getProductId().equals("P004"));
    }

    @Test
    @DisplayName("가져오려고 하는 상세 상품이 없는 경우")
    void  detailProductNotFound() throws Exception{
        try {
            ProductDetailDto productDetailDto = productService.findProductDetailById("P004");
        }catch (NotFoundException e){
            assertTrue(e.getMessage().equals("해당 품목을 찾을 수 없습니다."));
        }

    }


    @Test
    @DisplayName("서비스 상품 삭제")
    void deleteProduct() throws Exception {
        //상품 삭제시 다른 매니저가 삭제할 수 도 있으므로 해당 상품의 registerManager 만 삭제할수 있도록 하기.
        productService.deleteProduct("P004");
        try{
            productService.findProductDetailById("P004");
        }catch (NotFoundException e){
            assertTrue(e.getMessage().equals("해당 품목을 찾을 수 없습니다."));
        }
    }

    @Test
    @DisplayName("삭제하려는 상품이 없는 경우")
    void deleteProductNotFound() throws Exception {


        try {
            productService.deleteProduct("hrAEAJFNDA151AZ");
        }catch (NotFoundException e) {
            assertTrue(e.getMessage().equals("삭제하려는 상품이 존재하지 않습니다."));
        }


    }

    @Test
    @DisplayName("상품 수정")
    void  updateProduct() throws Exception {

        //수정 같은 경우도 registermanager만 수정할 수 있도록.

        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setProductId("P004");
        productUpdateDto.setProductStatus("EVENT");
        productUpdateDto.setPrice(99999);
        productUpdateDto.setRegisterManager("수정자");

        productService.updateProduct(productUpdateDto);

        ProductDetailDto productDetailDto = productService.findProductDetailById(productUpdateDto.getProductId());

        assertTrue( productDetailDto.getProductId().equals(productUpdateDto.getProductId()));
        assertTrue(productDetailDto.getProductStatus().equals(productUpdateDto.getProductStatus()));
        assertTrue(productDetailDto.getPrice()==productUpdateDto.getPrice());
        assertTrue(productDetailDto.getRegisterManager().equals(productUpdateDto.getRegisterManager()));


    }

    @Test
    @DisplayName("상품 ID 가 없을 경우 수정")
    void updateProductNotFound() throws Exception {
        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setProductId("NOTFOUNDPRODUCT");
        productUpdateDto.setProductStatus("asdf");
        productUpdateDto.setPrice(123461347);
        productUpdateDto.setRegisterManager("수정자");

        try {
            productService.updateProduct(productUpdateDto);
        }catch (NotFoundException e){
            assertTrue(e.getMessage().equals("수정 물품이 존재하지 않습니다."));
        }



    }

    /*
    * 상품 상세 페이지에 들어갔을 때 조회수를 바로 올리는게 맞는지. 상품에 조회수를 두는게 좋은가?
    *       -> 무신사 조회수 있네...
    *  들어가자 마자 바로 올리는게 좋은지? 들어가서 몇초후에 조회수가 올라가게 하는것이 좋은지
    * -> 몇초후에 조회수가 올라가게 하는게 좋은가?
    *
    * */



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