package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescription.service.ProductDescriptionService;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import com.fastcampus.toyproject2.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDaoMysql productDao;
    private final StockDaoMysql stockDaoMysql;
    private final ProductDescriptionDaoMysql productDescriptionDao;
    private final ProductDescriptionImgDaoMysql productDescriptionImgDao;

    private final FileService fileService;
    private final View error;
    private final ProductDescriptionService productDescriptionService;

    @Value("${productImgLocation}")
    private String imgLocation;

    @Value(("${productRepImgLocation}"))
    private String imgRepLocation;




    /*
    *       상품 등록(상세 설명, 상세설명 img를 재사용하는 경우)
    *
    * */
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {


        System.out.println(productRegisterDto);

        registerProductAndRepImg(productRegisterDto, productRepImg);



        return productRegisterDto.getName();
    }


    /*
    *       상품 등록(상세 설명, 상세설명 img 를 생성하는 경우)
    *
    * */
    @Transactional
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg
            , List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs) throws Exception {


        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        /*
        * ProductDescription 저장
        * */

        ProductDescription productDescription = ProductDescription.builder()
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .description(productDescriptionDto.getDescription())
                .modifyDatetime(LocalDateTime.now())
                .build();

        System.out.println("productDescription : " + productDescription);
        productDescriptionDao.insert(productDescription);
        System.out.println("productDescription 생성");


        /*
         * ProductDescriptionImg 저장
         * */


        List<ProductDescriptionImg> imgList = new ArrayList<>();
        //순서를 Mapper에서 만들어 놓을까.
        byte i =1;

        for(MultipartFile file : DescriptionImgs){
            System.out.println("이미지 저장 시작");

            String filename = file.getOriginalFilename();

            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            //이런 부분 클래스(ProductDescriptionImg) 안에서 정의해도 되는가?
            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.DESCRIPTION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();

            imgList.add(productDescriptionImg);
        }

        i=1;

        for(MultipartFile file : productImgs){
            System.out.println("이미지  표시 저장 시작");

            String filename = file.getOriginalFilename();
            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.REPRESENTATION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();
            imgList.add(productDescriptionImg);
        }

        //img 먼저 생성하고 만드는게 나을지, img를 만들기 전에 생성하는게 좋을 지.

        System.out.println("imgs 저장");
        imgList.forEach(x->System.out.println(x.getProductDescriptionId()));
        productDescriptionImgDao.insert(imgList);


        registerProductAndRepImg(productRegisterDto, productRepImg);

        return  productRegisterDto.getName();
    }


    //한번 연결로 다중 인서트 작업test.
    @Transactional
    public String registerSavetest(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg
            , List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs) throws Exception{

        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        /*
         * ProductDescription 객체 생성.
         * */

        ProductDescription productDescription = ProductDescription.builder()
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .description(productDescriptionDto.getDescription())
                .modifyDatetime(LocalDateTime.now())
                .build();
//        productDescriptionDao.insert(productDescription);

        /*
        * 대표 사진 저장.
        *
        * */

        String repFilename =productRepImg.getOriginalFilename();

        String repFileCode = fileService.uploadFile(imgRepLocation, repFilename, productRepImg.getBytes());
        System.out.println("저장 완료");



        /*
         * Product 저장.
         * */



        Product registerProduct = Product.builder()
                .productId(productRegisterDto.getProductId())
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .categoryId(productRegisterDto.getCategoryId())
                .brandId(productRegisterDto.getBrandId())
                .name(productRegisterDto.getName())
                .repImg(repFileCode)
                .price(productRegisterDto.getPrice())
                .registerManager(productRegisterDto.getManagerName())
                .starRating(0F)
                .isDisplayed(Product.DEFAULT_DISPLAY)
//                .reviewCount(Product.DEFAULT_NUM)
//                .viewCount(Product.DEFAULT_NUM)
//                .starRating(Product.DEFAULT_NUM)//float
//                .salesQuantity(Product.DEFAULT_NUM)
                .build();
        /*
        * 재고 리스트 생성.
        * */


        List<Stock> stocks = new ArrayList<>();

        for(int i=0; i<productRegisterDto.getColor().size();i++){
            Stock stock = Stock.builder()
                    .productId(registerProduct.getProductId())
                    .color(productRegisterDto.getColor().get(i))
                    .size(productRegisterDto.getSize().get(i))
                    .quantity(productRegisterDto.getQuantity().get(i))
                    .build();

            stocks.add(stock);

        }




        /*
         * ProductDescriptionImg 객체 생성
         * */


        List<ProductDescriptionImg> imgList = new ArrayList<>();
        //순서를 Mapper에서 만들어 놓을까.
        byte i =1;

        for(MultipartFile file : DescriptionImgs){
            System.out.println("이미지 저장 시작");

            String filename = file.getOriginalFilename();

            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            //이런 부분 클래스(ProductDescriptionImg) 안에서 정의해도 되는가?
            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.DESCRIPTION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();

            imgList.add(productDescriptionImg);
        }

        i=1;

        for(MultipartFile file : productImgs){
            System.out.println("이미지  표시 저장 시작");

            String filename = file.getOriginalFilename();
            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());

            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescription.getProductDescriptionId())
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.REPRESENTATION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();
            imgList.add(productDescriptionImg);
        }

        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("ProductDescription",productDescription);
        testMap.put("stockList",stocks);
        testMap.put("imgList",imgList);
        testMap.put("Product", registerProduct);

        productDao.insertTest(testMap);

        return productRegisterDto.getName();

    }

    /*
    *       상품 상세 정보
    *
    * */

    @Transactional(readOnly = true)
    public ProductDetailDto findProductDetailById(String productId) throws Exception {
        //product dao에서 한번에 리스트로 불러올지
        //다른 dao 사용해서 img는 리스트로 나머지는 단일로 불러올지 시험.
        ProductDetailDto productDetailDto= productDao.findProductDetailById(productId);
        ProductDescriptionDto productDescriptionDto= productDescriptionDao.findByProductId(productId);
        productDetailDto.setProductDescription(productDescriptionDto);
        List<ProductDescriptionImgDetailDto> imgList = productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionDto.getProductDescriptionId());

        List<ProductDescriptionImgDetailDto> desImgs = new ArrayList<>();
        List<ProductDescriptionImgDetailDto> repImgs = new ArrayList<>();



        for(ProductDescriptionImgDetailDto imgs : imgList){
            if(imgs.getKindOf().equals(ProductDescriptionImg.DESCRIPTION))
                desImgs.add(imgs);
            else repImgs.add(imgs);

        }
        productDetailDto.setDescriptionImages(desImgs);
        productDetailDto.setProductImages(repImgs);

        return productDetailDto;
    }



    /*
    *       커서 기반 상품 페이징
    *
    *
    *   별점 - key : starRate , value : DESC, ASC
    *    키워드 - key: keyword , value : "문자열" -> (키워드는 페이지방식으로 ?)
    *    날짜순 - key : byDate , value : DESC, ASC
    *    판매량순 - key : sales , value : DESC, ASC
    *    가격순 -  key : price , value : DESC, ASC
    *    카테고리 - key : category , value : category_id
    *    가격대 정해서 볼수도 있도록.
    * */
    @Transactional(readOnly = true)
    public List<ProductListDto> findCursorList(HashMap<String, Object> map) throws Exception {
        //Map으로 해서 들고오던지 Dto로 해서 들고오던지 하기.
        return productDao.findCursorList(map);
    }


    /*
    *       페이지 기반 상품 페이징
    *
    * */
    @Transactional(readOnly = true)
    public List<ProductListDto> findPageList(PageInfo pageInfo) throws Exception {

        HashMap<String ,Object> pageMap = PageInfo.toHashMap(pageInfo);
        pageInfo.productCountCal(productDao.countProduct(pageMap));
        return productDao.findPageList(pageMap);
    }



    /*
    *       상품 등록시 상품과 상품 대표 이미지 저장.
    *
    *
    * */
    private void registerProductAndRepImg(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {


        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        /*
        * 대표 사진 저장.
        * */

        String repFilename =productRepImg.getOriginalFilename();

        String repFileCode = fileService.uploadFile(imgRepLocation, repFilename, productRepImg.getBytes());
        System.out.println("저장 완료");



        /*
         * Product 저장.
         * */



        Product registerProduct = Product.builder()
                .productId(productRegisterDto.getProductId())
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .categoryId(productRegisterDto.getCategoryId())
                .brandId(productRegisterDto.getBrandId())
                .name(productRegisterDto.getName())
                .repImg(repFileCode)
                .price(productRegisterDto.getPrice())
                .registerManager(productRegisterDto.getManagerName())
                .starRating(0F)
                .isDisplayed(Product.DEFAULT_DISPLAY)
//                .reviewCount(Product.DEFAULT_NUM)
//                .viewCount(Product.DEFAULT_NUM)
//                .starRating(Product.DEFAULT_NUM)//float
//                .salesQuantity(Product.DEFAULT_NUM)
                .build();

        productDao.insert(registerProduct);


        /*
         * Stock 저장
         * */

        List<Stock> stocks = new ArrayList<>();

        for(int i=0; i<productRegisterDto.getColor().size();i++){
            Stock stock = Stock.builder()
                    .productId(registerProduct.getProductId())
                    .color(productRegisterDto.getColor().get(i))
                    .size(productRegisterDto.getSize().get(i))
                    .quantity(productRegisterDto.getQuantity().get(i))
                    .build();

            stocks.add(stock);

        }

        stockDaoMysql.insert(stocks);

    }




}
