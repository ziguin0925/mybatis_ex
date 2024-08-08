package com.fastcampus.toyproject2.util;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {



    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileCode = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath +"/"+ fileCode;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return fileCode;
    }


    public void deleteFile(String filePath) throws IOException {
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) deleteFile.delete();

    }


}
