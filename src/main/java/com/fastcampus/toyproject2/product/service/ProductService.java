package com.fastcampus.toyproject2.product.service;

import com.fastcampus.toyproject2.category.dao.CategoryDao;
import com.fastcampus.toyproject2.category.dao.CategoryDaoMysql;
import com.fastcampus.toyproject2.category.dto.CategoryHierarchyDto;
import com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass.DuplicateProductDescriptionIdException;
import com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass.DuplicateProductIdException;
import com.fastcampus.toyproject2.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.product.dto.pagination.cursor.ProductCursorPageDto;
import com.fastcampus.toyproject2.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImgRegisterDto;
import com.fastcampus.toyproject2.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.stock.dto.Stock;
import com.fastcampus.toyproject2.util.FileService;
import com.fastcampus.toyproject2.util.S3FileService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDaoMysql productDao;
    private final StockDaoMysql stockDao;
    private final ProductDescriptionDaoMysql productDescriptionDao;
    private final ProductDescriptionImgDaoMysql productDescriptionImgDao;

    private final FileService fileService;
    private final S3FileService s3FileService;
    private final CategoryDaoMysql categoryDao;





    //상품 등록.
    @Transactional
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg
            , List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs) throws Exception {

        //null이면 새로 생성 하는거. readonly 못 할까 controller 에서 descriptionService로 읽어야하나.
        ProductDescriptionDto productDescriptionCheck = productDescriptionDao.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());

        /*
         * Product 객체 생성, 대표 사진 저장, stockList 객체 생성 후 Map에 넣기.
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


        //상세 설명을 만드는 경우
        //요청 json이 어떻게 오는지 확인해봐야할듯... DescriptionImgs 있는데 텅 비었다면 isEmpty로 해도 될거같은데.

        try{

            if (productDescriptionCheck == null && DescriptionImgs != null) {

                //ProductDescription 객체 생성.
                ProductDescription productDescription = ProductRegisterDto.toProductDescription(productRegisterDto);

                //ProductDescriptionImg 저장 및 객체 생성
                List<ProductDescriptionImg> imgList = fileService.toImageList(DescriptionImgs, productImgs, productDescription.getProductDescriptionId());

                productDescriptionDao.insert(productDescription);
                productDescriptionImgDao.insert(imgList);
            }
            productDao.insertTest(registerProduct);
            stockDao.insert(stocks);
        }catch (Exception e ){
            e.printStackTrace();
        }

        return productRegisterDto.getName();
    }


    @Transactional
    public HashMap<String,Object> registerPresignedUrlSave(ProductRegisterDto productRegisterDto
            , String productRepImg
            , List<ProductDescriptionImgRegisterDto> DescriptionImgs
            , List<ProductDescriptionImgRegisterDto> productImgs) throws Exception {

        //null이면 새로 생성 하는거. readonly 못 할까 controller 에서 descriptionService로 읽어야하나.
        ProductDescriptionDto productDescriptionCheck = productDescriptionDao.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());


        String repFileCode = s3FileService.createRepImgPath(productRegisterDto.getBrandId(), productRegisterDto.getProductId(), productRepImg);
        HashMap<?,?> varMap = new HashMap<>(3);
        varMap.put("repImg", repFileCode);


        Product registerProduct = ProductRegisterDto.toProduct(productRegisterDto, repFileCode);


        List<Stock> stocks = ProductRegisterDto.toStockList(productRegisterDto);


        //상세 설명을 만드는 경우
        //요청 json이 어떻게 오는지 확인해봐야할듯... DescriptionImgs 있는데 텅 비었다면 isEmpty로 해도 될거같은데.

        try{

            if (productDescriptionCheck == null && DescriptionImgs != null) {

                //ProductDescription 객체 생성.
                ProductDescription productDescription = ProductRegisterDto.toProductDescription(productRegisterDto);

                //ProductDescriptionImg 저장 및 객체 생성
                List<ProductDescriptionImg> imgList = fileService.toImageList(DescriptionImgs, productImgs, productDescription.getProductDescriptionId());

                productDescriptionDao.insert(productDescription);
                productDescriptionImgDao.insert(imgList);
            }
            productDao.insertTest(registerProduct);
            stockDao.insert(stocks);
        }catch (Exception e ){
            e.printStackTrace();
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
            //여기 처리
            System.out.println(e.getMessage());
        }
        if(productDetailDto==null){
            throw new NotFoundException("해당 품목을 찾을 수 없습니다.");
        }

        //상품 설명 찾기.
        ProductDescriptionDto productDescriptionDto= productDescriptionDao.findByProductId(productId);
        productDetailDto.setProductDescription(productDescriptionDto);

        //상품 설명ID 로 상품 설명 이미지, 상품 이미지 찾기.
        List<ProductDescriptionImgDetailDto> imgList = productDescriptionImgDao.findAllByProductDescriptionId(productDescriptionDto.getProductDescriptionId());

        List<ProductDescriptionImgDetailDto> desImgs = new ArrayList<>();
        List<ProductDescriptionImgDetailDto> repImgs = new ArrayList<>();

        //상품 이미지와 상품 설명 이미지 분류.
        for(ProductDescriptionImgDetailDto imgs : imgList){
            if(imgs.getKindOf().equals(ProductDescriptionImg.DESCRIPTION))
                desImgs.add(imgs);
            else repImgs.add(imgs);

        }

        //상품 이미지 Dto에 추가.
        productDetailDto.setDescriptionImages(desImgs);
        productDetailDto.setProductImages(repImgs);



        //해당 상품의 카테고리 Id 찾기. -> 상품에 아예 카테고리 이름도 넣어버릴까?
        List<CategoryHierarchyDto> categoryHierarchyDtos = categoryDao.findUpperCategoryHierarchyById(productDetailDto.getCategoryId());
        productDetailDto.setParentCategorys(categoryHierarchyDtos);





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
        map.put("size",Integer.parseInt(map.get("size").toString()));

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


    @Transactional(readOnly = true)
    public List<ProductAdminList> findProductAdminList() throws Exception {
        return productDao.findProductAdminList();
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

}




