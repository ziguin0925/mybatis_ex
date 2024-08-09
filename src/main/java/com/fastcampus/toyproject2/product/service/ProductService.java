package com.fastcampus.toyproject2.product.service;

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
import com.fastcampus.toyproject2.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Value("${productImgLocation}")
    private String imgLocation;

    @Value(("${productRepImgLocation}"))
    private String imgRepLocation;



    //저장 할때마자 findBy로 DB에 잘 저장 되었는지 확인이 필요한가?
    // throws Exception이 있는데
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {

        registerProductAndRepImg(productRegisterDto, productRepImg);


        return productRegisterDto.getName();
    }


    @Transactional
    public String registerSave(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg
            , MultipartFile[] DescriptionImgs
            , MultipartFile[] productImgs) throws Exception {


        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        /*
        * ProductDescription 저장
        * */

        System.out.println("productDescriptionDto ID : " + productDescriptionDto.getProductDescriptionId());
        ProductDescription productDescription = ProductDescription.builder()
                .productDescriptionId(productDescriptionDto.getProductDescriptionId())
                .description(productDescriptionDto.getProductDescription())
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




    private void registerProductAndRepImg(ProductRegisterDto productRegisterDto
            , MultipartFile productRepImg) throws Exception {


        ProductDescriptionDto productDescriptionDto = productRegisterDto.getProductDescriptionDto();

        /*
        * 대표 사진 저장.
        * */

        String repFilename =productRepImg.getOriginalFilename();
        String repFileCode = fileService.uploadFile(imgRepLocation, repFilename, productRepImg.getBytes());


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
