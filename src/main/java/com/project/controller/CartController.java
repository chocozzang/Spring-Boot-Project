package com.project.controller;

import com.project.entity.*;
import com.project.repository.FurnitureRepository;
import com.project.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    CartService cartService;

    @Autowired
    CartFurnitureService cartFurnitureService;

    @Autowired
    FurnitureService furnitureService;

    @Autowired
    MemberService memberService;

    @Autowired
    FurnitureImageService furnitureImageService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/list")
    public String getlist(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;

        String username = ((UserDetails) principal).getUsername();

        log.info("xx");
        log.info(username);
        log.info("yy");

        MemberEntity memberEntity = memberService.getMemberByEmail(username);

        Long cid = cartService.getCart(memberEntity.getMid());

        List<CartFurnitureEntity> cartFurnitureEntities = cartFurnitureService.findCF(cid);

        model.addAttribute("cartFurnitureList", cartFurnitureEntities);
        return "cart/cartpage";
    }

    @Transactional
    @PostMapping("/list")
    public String list(Model model, Long count, Long fid) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity memberEntity = memberService.getMemberByEmail(username);
        Long cid = cartService.getCart(memberEntity.getMid());

        log.info(username);

        CartFurnitureEntity checkIsThereAnyCartFurniture = cartFurnitureService.isitExists(fid, cid);

        log.info("here1");

        if (checkIsThereAnyCartFurniture != null) {

            log.info("here2");

            FurnitureEntity furniture = furnitureService.read(fid);
            Long temptotal = count + checkIsThereAnyCartFurniture.getFurnitureAmount();

            log.info(temptotal);
            log.info(count);
            log.info(checkIsThereAnyCartFurniture.getFurnitureAmount());


            if(temptotal > furniture.getFurnitureStock()) {

                log.info("here3");
                Long canBuy = furniture.getFurnitureStock() - checkIsThereAnyCartFurniture.getFurnitureAmount();
                FurnitureEntity furnitureEntity =  furnitureService.read(fid);
                List<String> img_urls = furnitureImageService.getAllUrl(fid);
                model.addAttribute("furniture", furnitureEntity);
                model.addAttribute("imgs", img_urls);
                model.addAttribute("num_of_imgs", img_urls.size());
                model.addAttribute("errormessage", "장바구니에 재고 수량을 초과하여 담으셨습니다. " + canBuy + "개까지만 구매가 가능합니다.");
                List<ReviewEntity> reviewEntities = reviewService.getReviews(fid);
                model.addAttribute("reviews", reviewEntities);
                model.addAttribute("cgStock", canBuy);
                return "furniture/furniture_detail";
            }

            else {

                log.info("here4");
                cartFurnitureService.updateAmount(count, cid);
                List<CartFurnitureEntity> cartFurnitureEntities = cartFurnitureService.findCF(cid);
                model.addAttribute("cartFurnitureList", cartFurnitureEntities);
                return "cart/cartpage";
            }

        }

        CartEntity cartEntity = cartService.getMyCart(cid);
        FurnitureEntity furnitureEntity = furnitureService.readForCF(fid);
        CartFurnitureEntity cartFurniture = new CartFurnitureEntity();
        cartFurniture.setCartEntity(cartEntity);
        cartFurniture.setFid(fid);
        cartFurniture.setFurnitureName(furnitureEntity.getFurnitureName());
        cartFurniture.setFurnitureAmount(count.intValue());
        cartFurniture.setFurniturePrice(furnitureEntity.getFurniturePrice().intValue());
        cartFurniture.setFurnitureSubtotal(furnitureEntity.getFurniturePrice().intValue() * count.intValue());
        cartFurniture.setFileURL(furnitureEntity.getFileUrl());
        cartFurnitureService.assign(cartFurniture);
        List<CartFurnitureEntity> cartFurnitureEntities = cartFurnitureService.findCF(cid);
        model.addAttribute("cartFurnitureList", cartFurnitureEntities);
        return "cart/cartpage";
    }


    //특정상품삭제
    @PostMapping("/delete/{cfid}")
    public String CartDelete(@PathVariable("cfid") Long cfid) {
        log.info("cfid is what : " + cfid);
        cartFurnitureService.delete(cfid);
        return "redirect:/cart/list";
    }

    //장바구니 물품 주문/구매페이지로 보내기
    @GetMapping("/order/{mid}")
    public String CartPayment(@PathVariable("mid") String mid, OrderEntity order, Model model) {

        return "redirect:/order/orderpage";
    }

    @PostMapping("/insert")
    public String insert(Model model, Long count, Long fid) {
        log.info(count);
        log.info(fid);
        model.addAttribute("count", count);
        model.addAttribute("fid", fid);
        return "cart/test";
    }

}
