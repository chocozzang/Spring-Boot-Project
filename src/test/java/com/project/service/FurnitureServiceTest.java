package com.project.service;

import com.project.repository.FurnitureImageRepository;
import com.project.repository.FurnitureRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class FurnitureServiceTest {

    @Autowired
    FurnitureService furnitureService;

    @Autowired
    FurnitureRepository furnitureRepository;

    @Autowired
    FurnitureImageRepository furnitureImageRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            String path = "C:/furniture/images";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    //@Test
//    @DisplayName("상품 등록 테스트")
//    void saveItem() throws Exception {
//        FurnitureDTO furnitureDTO = new FurnitureDTO();
//        furnitureDTO.setFurnitureName("test furniture");
//        furnitureDTO.setFurniturePrice(Long.valueOf(10000));
//        furnitureDTO.setFurnitureHeight(10);
//        furnitureDTO.setFurnitureLength(20);
//        furnitureDTO.setFurnitureWidth(30);
//        furnitureDTO.setFurnitureLikes(45);
//        furnitureDTO.setFurnitureStock(Long.valueOf(6));
//        furnitureDTO.setFurnitureType("BED");
//        furnitureDTO.setFurnitureScoreAvg(10.2);
//
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//
//        Long FurnitureId = furnitureService.saveFurniture(furnitureDTO, multipartFileList);
//
//        List<FurnitureImage> furniture_ImageList = furnitureImageRepository.findAllByOrderByFiidAsc();
//
//        Furniture furniture = furnitureRepository.findById(FurnitureId).orElseThrow(EntityNotFoundException::new);
//
//        log.info(FurnitureId);
//        for (FurnitureImage fi : furniture_ImageList) {
//            log.info(fi);
//        }
//
//        assertEquals(furnitureDTO.getFurnitureName(), furniture.getFurnitureName());
//        assertEquals(multipartFileList.get(0).getOriginalFilename(), furniture_ImageList.get(0).getOriImgName());
//    }
}