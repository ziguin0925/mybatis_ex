package com.fastcampus.toyproject2.util;

import java.util.Arrays;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.fastcampus.toyproject2.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3FileService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    //Controller나 다른 Service에서 이용할 메서드.
    public String upload(MultipartFile image) {
        //입력 받은 파일이 비었는지 확인.
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new RuntimeException("ErrorCode.EMPTY_FILE_EXCEPTION");
        }
        //uploadImage를 호출하여 S3에 저장된 이미지의 public url을 반환한다.
        return this.uploadImage(image);
    }

    //
    private String uploadImage(MultipartFile image) {

        //파일 확장자가 올바른지 확인하는 메서드.
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            //S3에 직접 올리는 메서드.
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    //파일 확장자가 올바른지.
    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf("."); // .이 없으면 -1반환
        if (lastDotIndex == -1) {
            throw new RuntimeException("ErrorCode.NO_FILE_EXTENTION");
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new RuntimeException("올바른 파일 확장자 명이 아닙니다.");
        }
    }

    //S3에 직접 업로드 하는 메서드.
    private String uploadImageToS3(MultipartFile image) throws IOException {
        //원본 파일 명
        String originalFilename = image.getOriginalFilename();

        //확장자 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));

        //s3에 저장될  파일 명(UUID 10자 + 원본파일명)
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename;


        // 바이트를 순서대로 읽는다고함.
        InputStream is = image.getInputStream();

        //image를 byte[] 로 전환 -> image.getBytes();와 동일한 결과.
        //getInputStream을 통해 스트림을 사용하면 메모리 효율이 좋다고 함 -> 왜 좋을까
        //파일 크기가 작고 메모리 문제 고려x -> getBytes()
        //대용량 파일 > getInputStream().
        byte[] bytes = IOUtils.toByteArray(is);


        //메타 데이터란?
        ObjectMetadata metadata = new ObjectMetadata();
        //multipartfile - 해당 파일의 ContentType 설정.
        metadata.setContentType("image/" + extention);
        //파일의 크기.
        metadata.setContentLength(bytes.length);
        //바이트 배열을 차례로 읽어 들이기 위한 클래스
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            // put image to S3
            amazonS3.putObject(putObjectRequest);
        }catch (Exception e){
            throw new RuntimeException("s3로 파일 저장에 실패했습니다.");
        }finally {
            byteArrayInputStream.close();
            is.close();
        }

        //해당 버킷에 파일을 저장한 파일 url을 가져온다.
        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new RuntimeException("파일을 삭제하는데 실패했습니다. IOException");
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new RuntimeException("파일을 삭제하는데 실패했습니다. IOException");
        }
    }

    public String getFile(String fileName){
        //getUrl(버킷 이름, 파일 이름(+확장자)
        URL url = amazonS3.getUrl(bucketName,"1234.jpg");
        return url.toString();
    }
}