package com.fastcampus.toyproject2.productDescriptionImg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
public class ProductDescriptionImg {

    public static String DEFAULT_USE ="Y";
    public static String DESCRIPTION = "DES";
    public static String REPRESENTATION = "REP";

    private Long productDescriptionImgId;

    private String productDescriptionId;

    private String name;

    private byte orderNum;

    private String path;

    private String isUsed;

    private String kindOf;

    private Long size;



    @Builder
    public ProductDescriptionImg(Long productDescriptionImgId, String productDescriptionId, String name, byte orderNum, String path, String isUsed, String kindOf, Long size) {
        this.productDescriptionImgId = productDescriptionImgId;
        this.productDescriptionId = productDescriptionId;
        this.name = name;
        this.orderNum = orderNum;
        this.path = path;
        this.isUsed = isUsed;
        this.kindOf = kindOf;
        this.size = size;
    }

//    public static List<ProductDescriptionImg> toImgList(
//            List<MultipartFile> desImgs
//            , List<MultipartFile> repImgs
//            , String productDescriptionId) {
//
//
//    }
}
