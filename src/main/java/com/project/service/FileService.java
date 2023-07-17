package com.project.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Log
@Service
public class FileService {

    public String uploadFile(String uploadPath, String originFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        log.info("fileservice.uploadfile");
        log.info(fileUploadFullUrl);
        log.info(savedFileName);
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        log.info("error1");
        fos.write(fileData);
        log.info("error2");
        fos.close();
        log.info("error3");
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
