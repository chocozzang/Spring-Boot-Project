package com.project.controller;

import com.project.dto.MainFurnitureDTO;
import com.project.entity.*;
import com.project.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@Controller
public class MainController {

    @Autowired
    CartFurnitureService cartFurnitureService;
    @Autowired
    FurnitureService furnitureService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberEntity memberEntity;
    @Autowired
    BoardService boardService;
    @Autowired
    BoardFileService boardFileService;

    @GetMapping(value="/admin/members/list")
    public String memberlist(Model model) {
        model.addAttribute("memberList", memberService.getlist());
        return "member/memberList";
    }

    @GetMapping(value="/admin/members/delete/{mid}")
    public String memberdelete(@PathVariable Long mid) {
        memberService.delete(mid);
        return "redirect:/admin/members/list";
    }

    @GetMapping(value = "/main/")
    public String main(Model model) {

        List<FurnitureEntity> topthree = furnitureService.selectTopThree();
        List<BoardEntity> topBoard = boardService.selectTopTwo();
        BoardEntity firstBoard = new BoardEntity();
        BoardEntity secondBoard = new BoardEntity();

        String firstURL = null;
        String secondURL = null;

        if(topBoard.size() == 0) {
            firstBoard = null; secondBoard = null;
        }
        else if(topBoard.size() == 1) {
            firstBoard = topBoard.get(0);
            firstURL = boardFileService.selectURL(topBoard.get(0).getBid());
            secondBoard = null;
        }
        else {
            boolean condition = true;
            for (BoardEntity boardEntity : topBoard) {

                if(condition) {
                    firstBoard = boardEntity;
                    firstURL = boardFileService.selectURL(boardEntity.getBid());
                } else {
                    secondBoard = boardEntity;
                    secondURL = boardFileService.selectURL(boardEntity.getBid());
                }
                condition = !condition;
            }
        }

        model.addAttribute("topthreefurniture", topthree);
        model.addAttribute("topBoard", firstBoard);
        model.addAttribute("nextBoard", secondBoard);
        model.addAttribute("topURL", firstURL);
        model.addAttribute("nextURL", secondURL);
        return "main";
    }


//    @GetMapping(value="/")
//    public String main(String findwhat, Optional<Integer> page, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
//
//        if(findwhat != null) {
//            // log.info("xyasdfgasdgasgd");
//            Page<MainFurnitureDTO> furnitures = furnitureService.getMainFurniturePage(findwhat ,pageable);
//            List<MainFurnitureDTO> fs = furnitures.getContent();
//            // log.info(furnitures);
//            // log.info(fs.size());
//            model.addAttribute("furnitures",furnitures.getContent());
//            model.addAttribute("furnitureSearchDTO",findwhat);
////            model.addAttribute("maxPage",5);
//        }
//        else {
//            // log.info("qieyqoieuroiudfaoisdufoadisfa");
//            List<Furniture> furnitures = furnitureService.selectTopTen();
//            model.addAttribute("furnitures",furnitures);
//            model.addAttribute("furnitureSearchDTO", null);
//        }
//
//        /*return "redirect:/furniture/list";*/
//        return "furniture/mainpage";
//    }

}
