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
    *       이거 안쓰고 밑에꺼 쓰기. - 다중 쿼리로 db연결 한번 만.
    * */
//    @Transactional
//    public String registerSave(ProductRegisterDto productRegisterDto
//            , MultipartFile productRepImg
//            , List<MultipartFile> DescriptionImgs
//            , List<MultipartFile> productImgs) throws Exception {
//
//
//        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();
//
//        /*
//        * ProductDescription 객체 저장
//        * */
//
//        ProductDescription productDescription = ProductDescription.builder()
//                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
//                .description(productDescriptionDto.getDescription())
//                .modifyDatetime(LocalDateTime.now())
//                .build();
//
//        productDescriptionDao.insert(productDescription);
//
//
//        /*
//         * ProductDescriptionImg 저장
//         * */
//
//
//        List<ProductDescriptionImg> imgList = new ArrayList<>();
//        //순서를 Mapper에서 만들어 놓을까.
//        byte i =1;
//
//        for(MultipartFile file : DescriptionImgs){
//
//            String filename = file.getOriginalFilename();
//
//            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());
//
//            //이런 부분 클래스(ProductDescriptionImg) 안에서 정의해도 되는가?
//            ProductDescriptionImg productDescriptionImg
//                    = ProductDescriptionImg.builder()
//                    .productDescriptionId(productDescription.getProductDescriptionId())
//                    .name(filename)
//                    .path(imgLocation+fileCode)
//                    .orderNum(i++)
//                    .size(file.getSize())
//                    .kindOf(ProductDescriptionImg.DESCRIPTION)
//                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
//                    .build();
//
//            imgList.add(productDescriptionImg);
//        }
//
//        i=1;
//
//        for(MultipartFile file : productImgs){
//            System.out.println("이미지  표시 저장 시작");
//
//            String filename = file.getOriginalFilename();
//            String fileCode = fileService.uploadFile(imgLocation, filename, file.getBytes());
//
//            ProductDescriptionImg productDescriptionImg
//                    = ProductDescriptionImg.builder()
//                    .productDescriptionId(productDescription.getProductDescriptionId())
//                    .name(filename)
//                    .path(imgLocation+fileCode)
//                    .orderNum(i++)
//                    .size(file.getSize())
//                    .kindOf(ProductDescriptionImg.REPRESENTATION)
//                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
//                    .build();
//            imgList.add(productDescriptionImg);
//        }
//
//        //img 먼저 생성하고 만드는게 나을지, img를 만들기 전에 생성하는게 좋을 지.
//
//        imgList.forEach(x->System.out.println(x.getProductDescriptionId()));
//        productDescriptionImgDao.insert(imgList);
//
//
//        registerProductAndRepImg(productRegisterDto, productRepImg);
//
//        return  productRegisterDto.getName();
//    }


    //한번 연결로 다중 인서트 작업test.
    @Transactional
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg
            , List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs) throws Exception{


        /*
         * ProductDescription 객체 생성.
         * */

        ProductDescription productDescription = ProductRegisterDto.toProductDescription(productRegisterDto);

        /*
        * 대표 사진 저장.
        *
        * */

        String repFileCode = fileService.uploadRepImg(productRepImg);


        /*
         * Product 저장.
         * */

        Product registerProduct = ProductRegisterDto.toProduct(productRegisterDto, repFileCode);


        /*
        * 재고 리스트 생성.
        * */


        List<Stock> stocks = ProductRegisterDto.toStockList(productRegisterDto);

        /*
         * ProductDescriptionImg 객체 생성
         *
         * 이 부분은 좀 생각해보기.
         * */

        List<ProductDescriptionImg> imgList = fileService.toImageList(DescriptionImgs, productImgs, productDescription.getProductDescriptionId());

        HashMap<String, Object> registerMap = new HashMap<>();
        registerMap.put("ProductDescription",productDescription);
        registerMap.put("stockList",stocks);
        registerMap.put("imgList",imgList);
        registerMap.put("Product", registerProduct);

        productDao.insertTest(registerMap);

        return productRegisterDto.getName();

    }

    /*
    *       상품 상세 정보
    *       한번에 가져올 수 있는지 생각
    *       -> List로 가져오게 될 것 같은데.
    *
    * */

    @Transactional(readOnly = true)
    public ProductDetailDto findProductDetailById(String productId) throws Exception {
        //product dao에서 한번에 리스트로 불러올지
        //다른 dao 사용해서 img는 리스트로 나머지는 단일로 불러올지 시험.
        ProductDetailDto productDetailDto= productDao.findProductDetailById(productId);
        if(productDetailDto ==null) return null;

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
    *       상품 삭제
    *
    * */
    public void deleteProduct(String productId) throws Exception {
        productDao.deleteByProductId(productId);
    }

    public void updateProduct(ProductUpdateDto productUpdateDto) throws Exception {

        productDao.updateProduct(productUpdateDto);

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
