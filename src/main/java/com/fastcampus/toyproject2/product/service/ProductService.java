package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass.DuplicateProductDescriptionIdException;
import com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass.DuplicateProductIdException;
import com.fastcampus.toyproject2.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.product.dto.pagination.cursor.ProductCursorPageDto;
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
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

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
    *       상품 등록(상세 설명, 상세설명 img를 재사용하는 경우) -> 오버로딩 말고 하나의 메서드로 하기.
    *
    * */
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {

        HashMap<String, Object> registerMap = registerProductAndRepImg(productRegisterDto, productRepImg);

        try {
            productDao.insert(registerMap);
        }catch (DuplicateKeyException e) {
            throw new DuplicateProductIdException(productRegisterDto.getProductId()+"는 이미 있는 Id 입니다.");
        }

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

        //null이면 새로 생성 하는거. readonly 못 할까 controller 에서 descriptionService로 읽어야하나.
        ProductDescriptionDto productDescriptionCheck = productDescriptionDao.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());

        /*
         * Product 객체 생성, 대표 사진 저장, stockList 객체 생성 후 Map에 넣기.
         * */
        HashMap<String, Object> registerMap = registerProductAndRepImg(productRegisterDto, productRepImg);



        //상세 설명을 만드는 경우
        //요청 json이 어떻게 오는지 확인해봐야할듯... DescriptionImgs 있는데 텅 비었다면 isEmpty로 해도 될거같은데.
        if(productDescriptionCheck == null && DescriptionImgs!=null && productImgs!=null){
            /*
             * ProductDescription 객체 생성.
             * */

            ProductDescription productDescription = ProductRegisterDto.toProductDescription(productRegisterDto);

            /*
             * ProductDescriptionImg 저장 및 객체 생성
             *
             * 이 부분은 좀 생각해보기. - DB 저장 에러시 삭제.
             * */

            List<ProductDescriptionImg> imgList = fileService.toImageList(DescriptionImgs, productImgs, productDescription.getProductDescriptionId());

            registerMap.put("ProductDescription",productDescription);
            registerMap.put("imgList",imgList);
        }



        try{
            productDao.insert(registerMap);
        }catch (IllegalAccessException e){


            //이부분 에러 처리 더 하기.


            System.out.println(e.getMessage());
        } catch (DuplicateKeyException e){
            if(e.getMessage().contains("product_description.PRIMARY")){
                throw new DuplicateProductDescriptionIdException(productDescriptionCheck.getProductDescriptionId()+"는 이미 있는 Id 입니다.");
            }
            throw new DuplicateProductIdException(productRegisterDto.getProductId()+"는 이미 있는 Id 입니다.");
        }

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
        ProductDetailDto productDetailDto = null;
        try {
            productDetailDto = productDao.findProductDetailById(productId);
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
        if(productDetailDto==null){
            throw new NotFoundException("해당 품목을 찾을 수 없습니다.");
        }


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
    *       나중에 동적 sortCode를 동적 쿼리로 처리할 지 Service 단에서 처리할 지 생각해보기.
    *
    * */
    @Transactional(readOnly = true)
    public List<ProductCursorPageDto> findCursorList(HashMap<String, Object> map) throws Exception {
        //Map으로 해서 들고오던지 Dto로 해서 들고오던지 하기.

        //나중에 하드코딩 지우기 -> DTO로 만들어서 DTO 내부에서 처리하든지.
        if(map.get("sortCode").equals("RANKING")) {
            return productDao.findCursorPageListOrderByRank(map);
        }else if(map.get("sortCode").equals("SALES")){
            return productDao.findCursorPageListOrderBySales(map);
        }else if(map.get("sortCode").equals("HIGHPRICE")){
            return productDao.findCursorPageListByHighPrice(map);
        }else if(map.get("sortCode").equals("LOWPRICE")){
            return productDao.findCursorPageListByLowPrice(map);
        }else if(map.get("sortCode").equals("LATEST")){
            return productDao.findCursorPageListByLastest(map);
        }

        return productDao.findCursorList(map);
    }


    /*
    *       페이지 기반 상품 페이징
    *
    * */
    @Transactional(readOnly = true)
    public List<ProductPageDto> findPageList(PageInfo pageInfo) throws Exception {

        HashMap<String ,Object> pageMap = PageInfo.toHashMap(pageInfo);
        pageInfo.productCountCal(productDao.countProduct(pageMap));
        return productDao.findPageList(pageMap);
    }


    /*
    *       상품 삭제
    *
    * */
    public int deleteProduct(String productId) throws Exception {

        int deleteNum = 0;

        try {
            deleteNum = productDao.deleteByProductId(productId);
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
        if(deleteNum==0){
            throw new NotFoundException("삭제하려는 상품이 존재하지 않습니다.");
        }

        return deleteNum;
    }

    public void updateProduct(ProductUpdateDto productUpdateDto) throws NotFoundException {
        int i=0;
        try {
            i =productDao.updateProduct(productUpdateDto);
        }catch (Exception e ){
                System.out.println(e.getMessage());
        }
        if(i==0){
            throw new NotFoundException("수정 물품이 존재하지 않습니다.");
        }

    }

    /*
    *       상품 등록시 상품과 상품 대표 이미지 저장.
    *
    *
    * */
    private HashMap<String, Object> registerProductAndRepImg(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {

        HashMap<String, Object> registerMap = new HashMap<>();

        /*
        * 대표 사진 저장.
        * */
        String repFileCode = fileService.uploadRepImg(productRepImg);


        /*
         * Product 객체 생성.
         * */

        Product registerProduct = ProductRegisterDto.toProduct(productRegisterDto, repFileCode);
        /*
         * Stock 저장
         * */
        List<Stock> stocks = ProductRegisterDto.toStockList(productRegisterDto);

        registerMap.put("Product",registerProduct);
        registerMap.put("stockList",stocks);
        return registerMap;


    }




}
