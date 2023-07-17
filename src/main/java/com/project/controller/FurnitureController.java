package com.project.controller;

import com.project.dto.*;
import com.project.entity.*;
import com.project.service.CartFurnitureService;
import com.project.service.FurnitureImageService;
import com.project.service.FurnitureService;
import com.project.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class FurnitureController {

    @Autowired
    FurnitureService furnitureService;
    @Autowired
    FurnitureImageService furnitureImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    CartFurnitureService cartFurnitureService;

    @PostMapping(value = "/furniture/list")
    public String listDirect() {
        return "redirect:/furniture/list";
    }

    @GetMapping(value = "/furniture/list")
    public String list(String findwhat, Optional<Integer> page,
                       Model model, @PageableDefault(page = 0, size = 6, sort = "fid", direction = Sort.Direction.ASC) Pageable pageable) {

        if(findwhat != null) {
            int findpage;
            if(page.isEmpty()) findpage = 0;
            else findpage = page.get();
            if(page.isPresent()) log.info(page.get());
            Pageable findPageable = PageRequest.of(findpage, 6);
            Page<MainFurnitureDTO> paging = furnitureService.getMainFurniturePage(findwhat, findPageable);
            List<MainFurnitureDTO> fs = paging.getContent();
            int nowPage = paging.getPageable().getPageNumber() + 1;
            int startPage = 1;
            int endPage = Math.max(paging.getTotalPages(), 1);
            model.addAttribute("paging", paging);
            model.addAttribute("furniture_list",fs);
            model.addAttribute("now_page", nowPage);
            model.addAttribute("start_page", startPage);
            model.addAttribute("end_page", endPage);
            model.addAttribute("furnitureSearchDTO",findwhat);
        }
        else {
            Page<FurnitureEntity> paging = furnitureService.getList(pageable);
            int nowPage = paging.getPageable().getPageNumber() + 1;
            int startPage = 1;
            int endPage = Math.max(paging.getTotalPages(), 1);
            List<FurnitureEntity> furnList = paging.getContent();
            model.addAttribute("paging", paging);
            model.addAttribute("furniture_list", furnList);
            model.addAttribute("now_page", nowPage);
            model.addAttribute("start_page", startPage);
            model.addAttribute("end_page", endPage);
            model.addAttribute("furnitureSearchDTO", null);
        }

        return "furniture/furniture_list";
    }

    @GetMapping(value="/furniture/detail")
    public String detail(Model model, Long fid, Optional<Integer> page) {
        FurnitureEntity furnitureEntity =  furnitureService.read(fid);
        List<String> img_urls = furnitureImageService.getAllUrl(fid);
        model.addAttribute("furniture", furnitureEntity);
        model.addAttribute("imgs", img_urls);
        model.addAttribute("num_of_imgs", img_urls.size());
        List<ReviewEntity> reviewEntities = reviewService.getReviews(fid);
        model.addAttribute("reviews", reviewEntities);
        model.addAttribute("reviewsize", reviewEntities.size());
        model.addAttribute("pagenum", page.get());
        return "furniture/furniture_detail";
    }

    @GetMapping(value="/admin/furniture/register")
    public String register(@ModelAttribute FurnitureFormDTO furnitureFormDTO, Model model) {
        model.addAttribute("furnitureFormDTO", new FurnitureFormDTO());
        return "furniture/furniture_register";
    }

    @PostMapping(value="/admin/furniture/register")
    public String register(@Valid @ModelAttribute FurnitureFormDTO furnitureFormDTO, BindingResult bindingResult, Model model,
                           @RequestParam("furnitureImgFile") List<MultipartFile> furnitureImgFileList) {
        model.addAttribute("furnitureFormDTO", furnitureFormDTO);
        if(bindingResult.hasErrors()) {
            model.addAttribute("furnitureFormDTO", furnitureFormDTO);
            return "furniture/furniture_register";
        }
        if(furnitureImgFileList.get(0).isEmpty() && furnitureFormDTO.getFid() == null) {
            model.addAttribute("errormessage", "첫 번째 상품 이미지는 필수 입력 값 입니다.");
            return "furniture/furniture_register";
        }
        try {
            furnitureService.saveFurniture(furnitureFormDTO, furnitureImgFileList);
        } catch (Exception e) {
            model.addAttribute("errormessage", "상품 등록 중 에러가 발생하였습니다.");
            return "furniture/furniture_register";
        }return "redirect:/furniture/list";
    }

    @GetMapping(value="/admin/furniture/modify")
    public String furnitureDtl(@ModelAttribute FurnitureFormDTO furnitureFormDTO, Long fid, int page, Model model) {
        try {
            FurnitureDTO furnitureDTO = furnitureService.getFurnitureDtl(fid);
            FurnitureFormDTO furnitureFormDTO2 = FurnitureDTO.toForm(furnitureDTO);
            model.addAttribute("nowpage", page);
            for(FurnitureImgDTO fidto : furnitureDTO.getFurnitureImgDTOList()) {
            }
            model.addAttribute("furnitureFormDTO", furnitureFormDTO2);
        } catch (Exception e) {
            model.addAttribute("errormessage", "존재하지 않는 상품입니다.");
            model.addAttribute("furnitureFormDTO", new FurnitureFormDTO());
            return "furniture/furniture_register";
        }
        return "furniture/furniture_register";
    }

    @PostMapping(value="/admin/furniture/modify/{furnitureId}")
    public String furnitureUpdate(@PathVariable("furnitureId") Long fid, @Valid  @ModelAttribute FurnitureFormDTO furnitureFormDTO, BindingResult bindingResult, Model model,
                           @RequestParam("FurnitureImgFile") List<MultipartFile> furnitureImgFileList) {

        if(bindingResult.hasErrors()) {
            return "furniture/furniture_register";
        }

        if(furnitureImgFileList.get(0).isEmpty() && furnitureFormDTO.getFid() == null) {
            model.addAttribute("errormessage", "첫 번째 상품 이미지는 필수 입력 값 입니다.");
            return "furniture/furniture_register";
        }

        try {
            furnitureFormDTO.setFid(fid);
            FurnitureDTO furnitureDTO = FurnitureDTO.toDTO(furnitureFormDTO);
            furnitureService.updateFurniture(furnitureDTO, furnitureImgFileList);
        } catch (Exception e) {
            model.addAttribute("errormessage", "상품 등록 중 에러가 발생하였습니다.");
            return "furniture/furniture_register";
        }

        return "redirect:/furniture/list";
    }

    @GetMapping(value = "/admin/furniture/list")
    public String furnitureManage(Optional<Integer> page, FurnitureSearchDTO furnitureSearchDTO, Model model) {
        int realpage;
        if(page.isEmpty()) realpage = 0;
        else realpage = page.get();
        if(furnitureSearchDTO.getSearchQuery() != "") {
            if(page.isEmpty()) realpage = 0;
            else realpage = page.get();
        }
        Pageable pageable = PageRequest.of(realpage, 6);

        Page<FurnitureEntity> furnitures = furnitureService.getAdminFurniturePage(furnitureSearchDTO, pageable);
        model.addAttribute("furnitures", furnitures);
        model.addAttribute("furnitureSearchDTO", furnitureSearchDTO);
        model.addAttribute("start_page", 1);
        model.addAttribute("end_page", Math.max(furnitures.getTotalPages(), 1));
        model.addAttribute("nowpage", realpage);
        return "furniture/furniture_mng";
    }

    @PostMapping(value = "/admin/furniture/delete/{fid}")
    public String deleteFurniture(@PathVariable("fid") Long fid) {
        furnitureService.deleteF(fid);
        cartFurnitureService.deleteByFid(fid);
        return "redirect:/admin/furniture/list";
    }
}

