package com.fastcampus.toyproject2.Item.product.service;

import com.fastcampus.toyproject2.Item.category.dao.CategoryDaoMysql;
import com.fastcampus.toyproject2.Item.category.dto.CategoryHierarchyDto;
import com.fastcampus.toyproject2.Item.product.dao.ProductDaoMysql;
import com.fastcampus.toyproject2.Item.product.dto.*;
import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.Item.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.Item.product.dto.pagination.cursor.ProductCursorPageDto;
import com.fastcampus.toyproject2.Item.productDescription.dao.ProductDescriptionDaoMysql;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescription;
import com.fastcampus.toyproject2.Item.productDescription.dto.ProductDescriptionDto;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dao.ProductDescriptionImgDaoMysql;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ImagePathDto;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImg;
import com.fastcampus.toyproject2.Item.productDescriptionImg.dto.ProductDescriptionImgDetailDto;
import com.fastcampus.toyproject2.Item.stock.dao.StockDaoMysql;
import com.fastcampus.toyproject2.Item.stock.dto.Stock;
import com.fastcampus.toyproject2.ETC.util.S3FileService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDaoMysql productDao;
    private final StockDaoMysql stockDao;
    private final ProductDescriptionDaoMysql productDescriptionDao;
    private final ProductDescriptionImgDaoMysql productDescriptionImgDao;

    private final S3FileService s3FileService;
    private final CategoryDaoMysql categoryDao;





    //상품 등록.
    @Transactional
    public ImagePathDto registerSave(ProductRegisterDto productRegisterDto) throws Exception {

        ImagePathDto imagePathDto = new ImagePathDto();

        //null이면 새로 생성 하는거. readonly 못 할까 controller 에서 descriptionService로 읽어야하나.
        ProductDescriptionDto productDescriptionCheck = productDescriptionDao.findById(productRegisterDto.getProductDescriptionDto().getProductDescriptionId());


//        String repFilePath = s3FileService.createRepImgPath(productRegisterDto.getBrandId(), productRegisterDto.getProductId(), productRepImg);

        //상세 설명을 만드는 경우
        //요청 json이 어떻게 오는지 확인해봐야할듯... DescriptionImgs 있는데 텅 비었다면 isEmpty로 해도 될거같은데.
        //Dao 마다 예외 처리 - duplicatedKey등.
        try{
            if (productDescriptionCheck == null && !productRegisterDto.getDesImgs().isEmpty()) {

                //ProductDescription 객체 생성.
                ProductDescription productDescription = ProductRegisterDto.toProductDescription(productRegisterDto);

                //ProductDescriptionImg 저장 및 객체 생성
                List<ProductDescriptionImg> imgList = s3FileService.presigneUrltoImageList(productRegisterDto.getDesImgs(), productRegisterDto.getRepresenImgs(), productDescription.getProductDescriptionId(), productRegisterDto.getBrandId());

                productDescriptionDao.insert(productDescription);
                productDescriptionImgDao.insert(imgList);

                for(ProductDescriptionImg img: imgList){
                    if(img.getKindOf().equals(ProductDescriptionImg.DESCRIPTION)){
                        imagePathDto.addDescImgPath(img.getPath());
                    }else{
                        imagePathDto.addProdImgPath(img.getPath());
                        }
                }

            }
            String repFilePrefix = s3FileService.repImgPrefixPath(productRegisterDto.getBrandId(), productRegisterDto.getProductId(),productRegisterDto.getRepImg());

            Product registerProduct = ProductRegisterDto.toProduct(productRegisterDto,repFilePrefix);

            List<Stock> stocks = ProductRegisterDto.toStockList(productRegisterDto);

            imagePathDto.setRepImgPath(repFilePrefix);

            productDao.insertTest(registerProduct);
            stockDao.insert(stocks);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        //s3에 저장될 경로 배열 return
        return imagePathDto;
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
            productDetailDto.setRepImg(s3FileService.plusBucketPath(productDetailDto.getRepImg()));
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

            imgs.setPath(s3FileService.plusBucketPath(imgs.getPath()));

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

        List<ProductCursorPageDto> productList;

        //나중에 하드코딩 지우기 -> DTO로 만들어서 DTO 내부에서 처리하든지.
        if(map.get("sortCode").equals("RANKING")) {
            productList= productDao.findCursorPageListOrderByRank(map);
        }else if(map.get("sortCode").equals("SALES")){
            productList =  productDao.findCursorPageListOrderBySales(map);
        }else if(map.get("sortCode").equals("HIGHPRICE")){
            productList =  productDao.findCursorPageListByHighPrice(map);
        }else if(map.get("sortCode").equals("LOWPRICE")){
            productList =  productDao.findCursorPageListByLowPrice(map);
        }else if(map.get("sortCode").equals("LATEST")){
            productList = productDao.findCursorPageListByLastest(map);
        }else{
            productList =productDao.findCursorList(map);
        }
        for (ProductCursorPageDto productCursorPageDto : productList) {
            productCursorPageDto.setRepImg(s3FileService.plusBucketPath(productCursorPageDto.getRepImg()));
        }

        return productList;
    }

    /*
    *       페이지 기반 상품 페이징
    *
    * */
    @Transactional(readOnly = true)
    public List<ProductPageDto> findPageList(PageInfo pageInfo) throws Exception {

        HashMap<String ,Object> pageMap = PageInfo.toHashMap(pageInfo);
        pageInfo.productCountCal(productDao.countProduct(pageMap));
        List<ProductPageDto> productList= productDao.findPageList(pageMap);
        for(ProductPageDto product : productList){
            product.setRepImg(s3FileService.plusBucketPath(product.getRepImg()));
        }
        return productList;
    }

    @Transactional(readOnly = true)
    public List<ProductAdminList> findProductAdminList() throws Exception {
        List<ProductAdminList> productAdminList = productDao.findProductAdminList();

        for(ProductAdminList productAdmin : productAdminList){
            productAdmin.setRepImg(s3FileService.plusBucketPath(productAdmin.getRepImg()));
        }
        return productAdminList;
    }

    @Transactional
    public void viewCount(String productId) throws Exception {
        productDao.updateViewCount(productId);
    }

    /*
    *       상품 삭제
    *
    * */
    public void deleteProduct(String productId) throws Exception {
        int deleteNum = 0;
        try {
            //현재 productId에 해당하는 대표 이미지 s3에서 삭제
            s3FileService.deleteImageFromS3(productDao.findRepImgById(productId));

            //현재 productId DB에서 삭제
            deleteNum = productDao.deleteByProductId(productId);

        }catch (IOException e ){
            System.out.println(e.getMessage());
        }
        if(deleteNum==0){
            throw new NotFoundException("삭제하려는 상품이 존재하지 않습니다.");
        }
    }

    /*
    *   상품 수정
    *
    *   상품 수정 시 RepImg 도 같이 바뀌는 건지 확인.
    * */
    public String updateProduct(ProductUpdateDto productUpdateDto) throws NotFoundException {
        int i=0;

        //Repimg가 변경 되었을 때.
        if(productUpdateDto.getRepImg()!=null){
            try{
                s3FileService.deleteImageFromS3(productDao.findRepImgById(productUpdateDto.getProductId()));
            } catch (NotFoundException e) {
                throw new NotFoundException(e.getMessage());
            } catch (Exception e ){
                System.out.println(e.getMessage());
            }
            //productUpdateDto의 이미지 이름 s3Path+UUID 설정
            productUpdateDto.setRepImg(s3FileService.repImgPrefixPath(productUpdateDto.getBrandId(), productUpdateDto.getProductId(), productUpdateDto.getRepImg()));
        }


        try {
            i =productDao.updateProduct(productUpdateDto);
        }catch (Exception e ){
                System.out.println(e.getMessage());
        }
        if(i==0){
            throw new NotFoundException("수정 물품이 존재하지 않습니다.");
        }

        //null일 수 도 있음.
        return productUpdateDto.getRepImg();
    }
}




