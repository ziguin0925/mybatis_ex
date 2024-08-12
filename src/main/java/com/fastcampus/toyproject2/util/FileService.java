package com.fastcampus.toyproject2.util;

import com.fastcampus.toyproject2.productDescriptionImg.dto.ProductDescriptionImg;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${productImgLocation}")
    private String imgLocation;

    @Value(("${productRepImgLocation}"))
    private String imgRepLocation;



    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileCode = uuid + extension;
        String fileUploadFullUrl = uploadPath +"/"+ fileCode;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return fileCode;
    }

    public String uploadRepImg(MultipartFile productRepImg) throws IOException {

        return uploadFile(imgRepLocation, productRepImg.getOriginalFilename(), productRepImg.getBytes());


    }


    public void deleteFile(String filePath) throws IOException {
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) deleteFile.delete();

    }

    public List<ProductDescriptionImg> toImageList(
            List<MultipartFile> DescriptionImgs
            , List<MultipartFile> productImgs
            , String productDescriptionId ) throws IOException {

        List<ProductDescriptionImg> imgList = new ArrayList<>();
        //순서를 Mapper에서 만들어 놓을까.
        byte i =1;

        for(MultipartFile file : DescriptionImgs){
            System.out.println("이미지 저장 시작");

            String filename = file.getOriginalFilename();

            String fileCode = uploadFile(imgLocation, filename, file.getBytes());

            //이런 부분 클래스(ProductDescriptionImg) 안에서 정의해도 되는가?
            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescriptionId)
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
            String fileCode = uploadFile(imgLocation, filename, file.getBytes());

            ProductDescriptionImg productDescriptionImg
                    = ProductDescriptionImg.builder()
                    .productDescriptionId(productDescriptionId)
                    .name(filename)
                    .path(imgLocation+fileCode)
                    .orderNum(i++)
                    .size(file.getSize())
                    .kindOf(ProductDescriptionImg.REPRESENTATION)
                    .isUsed(ProductDescriptionImg.DEFAULT_USE)
                    .build();
            imgList.add(productDescriptionImg);
        }

        return imgList;

    }


}
