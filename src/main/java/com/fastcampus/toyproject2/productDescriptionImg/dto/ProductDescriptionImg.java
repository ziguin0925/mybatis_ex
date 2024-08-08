package com.fastcampus.toyproject2.productDescriptionImg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
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

    private LocalDateTime createDatetime;

    private LocalDateTime modifyDatetime;


    @Builder
    public ProductDescriptionImg(Long productDescriptionImgId, String productDescriptionId, String name, byte orderNum, String path, String isUsed, String kindOf, Long size, LocalDateTime createDatetime, LocalDateTime modifyDatetime) {
        this.productDescriptionImgId = productDescriptionImgId;
        this.productDescriptionId = productDescriptionId;
        this.name = name;
        this.orderNum = orderNum;
        this.path = path;
        this.isUsed = isUsed;
        this.kindOf = kindOf;
        this.size = size;
        this.createDatetime = createDatetime;
        this.modifyDatetime = modifyDatetime;
    }
}
