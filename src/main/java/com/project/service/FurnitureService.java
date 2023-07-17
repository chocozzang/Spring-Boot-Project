package com.project.service;

import com.project.dto.*;
import com.project.entity.FurnitureEntity;
import com.project.entity.FurnitureImageEntity;
import com.project.repository.FurnitureImageRepository;
import com.project.repository.FurnitureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class FurnitureService {

    @Autowired FurnitureRepository furnitureRepository;
    @Autowired FurnitureImageService furnitureImageService;
    @Autowired FurnitureImageRepository furnitureImageRepository;

    public Page<FurnitureEntity> getList(Pageable pageable) {
        return furnitureRepository.findAll(pageable);
    }

    public void regist(FurnitureDTO furnitureDTO) {
        furnitureRepository.save(furnitureDTO.toEntity());
    }

    public FurnitureEntity read(Long fid) {
        furnitureRepository.updateHitsByFid(fid);
        return furnitureRepository.findByFid(fid);
    }

    public FurnitureEntity readForCF(Long fid) {
        return furnitureRepository.findByFid(fid);
    }

    public Long saveFurniture(FurnitureFormDTO furnitureFormDTO, List<MultipartFile> furnitureImgFileList) throws Exception {

        FurnitureEntity furnitureEntity = furnitureFormDTO.createFurniture();

        String url = null;

        for(int i = 0; i < furnitureImgFileList.size(); i++) {
            FurnitureImageEntity furniture_image = new FurnitureImageEntity();
            furniture_image.setFurnitureEntity(furnitureEntity);
            furniture_image.setOriImgName(furnitureImgFileList.get(i).getOriginalFilename());
            if(i == 0) { furniture_image.setRepimgYn("Y"); url = furnitureImageService.saveItemImg(furniture_image, furnitureImgFileList.get(i), i);}
            else        { furniture_image.setRepimgYn("N"); furnitureImageService.saveItemImg(furniture_image, furnitureImgFileList.get(i), i);}
        }

        furnitureEntity.setFileUrl(url);

        furnitureRepository.save(furnitureEntity);

        return furnitureEntity.getFid();
    }

    @Transactional(readOnly = true) // 읽기 전용
    public FurnitureDTO getFurnitureDtl(Long fid) {
        List<FurnitureImageEntity> furnitureImageEntityList = furnitureImageRepository.findByFidOrderByFiidAsc(fid);
        List<FurnitureImgDTO> furnitureImgDTOList = new ArrayList<>();
        for(FurnitureImageEntity furnitureImageEntity : furnitureImageEntityList) {
            FurnitureImgDTO furnitureImgDTO = FurnitureImgDTO.of(furnitureImageEntity);
            furnitureImgDTOList.add(furnitureImgDTO);
        }

        FurnitureEntity furnitureEntity = furnitureRepository.findById(fid).orElseThrow(EntityNotFoundException::new);
        FurnitureDTO furnitureDTO = FurnitureDTO.of(furnitureEntity);
        furnitureDTO.setFurnitureImgDTOList(furnitureImgDTOList);
        return furnitureDTO;
    }

    public Long updateFurniture(FurnitureDTO furnitureDTO, List<MultipartFile> furnitureImageList) throws Exception{
        FurnitureEntity furnitureEntity = furnitureRepository.findById(furnitureDTO.getFid()).orElseThrow(EntityNotFoundException::new);
        furnitureEntity.updateFurniture(furnitureDTO);
        List<Long> furnitureImgIds = furnitureDTO.getFurnitureImgIds();
        for(int i = 0; i < furnitureImgIds.size(); i++) {
            furnitureImageService.updateFurnitureImg(furnitureImgIds.get(i), furnitureImageList.get(i));
        }

        return furnitureEntity.getFid();
    }

    public void deleteF(Long fid) {
        furnitureRepository.deleteById(fid);
    }

    @Transactional(readOnly = true)
    public Page<FurnitureEntity> getAdminFurniturePage(FurnitureSearchDTO furnitureSearchDTO, Pageable pageable) {
        return furnitureRepository.getAdminFurniturePage(furnitureSearchDTO, pageable);
    }

    public void updateStock(Long fid, int amount) {
        furnitureRepository.updateStock(fid, amount);
    }
    @Transactional(readOnly = true)
    public Page<MainFurnitureDTO> getMainFurniturePage(String findwhat, Pageable pageable) {
        return furnitureRepository.getMainFurniturePage(findwhat, pageable);
    }

    @Transactional(readOnly = true)
    public List<FurnitureEntity> selectTopThree() {
        return furnitureRepository.findTop3ByOrderByHitsDesc();
    }

}
