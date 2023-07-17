package com.project.controller;

import com.project.entity.FurnitureEntity;
import com.project.entity.ReviewEntity;
import com.project.service.CartFurnitureService;
import com.project.service.FurnitureService;
import com.project.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;


@Controller
@Log4j2
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    CartFurnitureService cartFurnitureService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    FurnitureService furnitureService;

    @PostMapping(value = "/register")
    public String mainpage(@RequestParam("cf[]") List<Long> cfs, String reviewContent) {

        log.info("mainmapping");
        log.info(reviewContent);
        String[] review = reviewContent.split(",");
        List<String> reviews = Arrays.stream(review).toList();

        for(int i = 0; i < cfs.size(); i++) {
            log.info(cfs.get(i));
            FurnitureEntity furnitureEntity = furnitureService.read(cfs.get(i));
            Long cfid = cartFurnitureService.findcfid(cfs.get(i));
            ReviewEntity newReview = new ReviewEntity();
            newReview.setFid(furnitureEntity);
            newReview.setReviewContent(reviews.get(i));
            reviewService.reService(newReview);
            cartFurnitureService.delete(cfid);
        }

        return "redirect:/furniture/list";
    }

}
