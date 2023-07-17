package com.project.service;

import com.project.entity.FurnitureImageEntity;
import com.project.repository.FurnitureImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class FurnitureImageService {

    @Value("${furnitureImgLocation}")
    private String furnitureImgLocation;

    private final FurnitureImageRepository furnitureImageRepository;
    private final FileService fileService;

    public String saveItemImg(FurnitureImageEntity furniture_image, MultipartFile multipartFile, int i) throws Exception {
        String oriImgName = furniture_image.getOriImgName();
        String imgFileName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)) {
            imgFileName = fileService.uploadFile(furnitureImgLocation, oriImgName, multipartFile.getBytes());
            imgUrl  = "/furniture/images/" + imgFileName;
        }

        furniture_image.updateFurnitureImg(oriImgName, imgFileName, imgUrl);
        furnitureImageRepository.save(furniture_image);

        if(i == 0) {
            return imgUrl;
        }
        else return null;
    }

    public void updateFurnitureImg(Long fid, MultipartFile furnitureImgFile) throws Exception{
        if(!furnitureImgFile.isEmpty()) {
            FurnitureImageEntity savedFurnitureImg = furnitureImageRepository.findById(fid).orElseThrow(EntityNotFoundException::new);
            if(!StringUtils.isEmpty(savedFurnitureImg.getImgFilename())) {
                fileService.deleteFile(furnitureImgLocation + "/" + savedFurnitureImg.getImgFilename());
            }

            String oriFileName = furnitureImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(furnitureImgLocation, oriFileName, furnitureImgFile.getBytes());
            String imgUrl = "/furniture/images/" + imgName;
            savedFurnitureImg.updateFurnitureImg(oriFileName, imgName, imgUrl);
        }
    }

    public String getUrl(Long fid) {
        List<FurnitureImageEntity> imgs = furnitureImageRepository.findByFurnitureId(fid);

        for(FurnitureImageEntity img : imgs) {
            if(img.getRepimgYn().equals("Y")) return img.getImgUrl();
        }
        return null;
    }

    public List<String> getAllUrl(Long fid) {
        List<FurnitureImageEntity> imgs = furnitureImageRepository.findByFurnitureId(fid);
        List<String> urls = new ArrayList<>();

        for(FurnitureImageEntity img : imgs) {
            if(!(img.getImgUrl().equals(""))) urls.add(img.getImgUrl());
        }

        return urls;
    }
}
