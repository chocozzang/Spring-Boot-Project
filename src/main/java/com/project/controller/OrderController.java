package com.project.controller;


import com.project.dto.OrderInfoDTO;
import com.project.entity.CartFurnitureEntity;
import com.project.entity.FurnitureEntity;
import com.project.entity.MemberEntity;
import com.project.repository.MemberRepository;
import com.project.service.CartFurnitureService;
import com.project.service.FurnitureService;
import com.project.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartFurnitureService cartFurnitureService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FurnitureService furnitureService;
    @Autowired
    CartFurnitureEntity cartFurnitureEntity;


    //    @PostMapping("/order")
//    public String order(Model model){
//        List<Cart_Furniture> orderList=cartFurnitureService.getlist();
//        model.addAttribute("orderList",orderList);
//        return "redirect:/order/";
//    }

    @PostMapping("/orderinfo")
    public String orderinfo(Model model, @RequestParam("cfids[]") List<Long> cfids,
                            @RequestParam("total_money") String totalMoney) {
        log.info("orderinfo postmapping = " + cfids);
        MemberEntity memberEntity = memberRepository.getById(1L);
//        log.info("order controller - get info : " + memberEntity);
        model.addAttribute("Member",memberEntity);
        model.addAttribute("cfids",cfids);
        model.addAttribute("total_money", Long.valueOf(totalMoney));
        List<CartFurnitureEntity> cart_furnitureEntity = new ArrayList<>();
        for(int i = 0; i < cfids.size(); i++) {
            cart_furnitureEntity.add(cartFurnitureService.search(Long.valueOf(cfids.get(i))));
//            log.info("orderinfo postmapping");
//            log.info(cfids.get(i));
        }
        model.addAttribute("cart_furnitures", cart_furnitureEntity);

        return "order/orderpage";
    }

    @Transactional
    //주문완료
    @PostMapping("/ordercomplete")
    public String complete(Model model, @RequestParam("cfids[]") List<Long> cfids, OrderInfoDTO orderInfoDTO) {
        log.info("/////////////");
        log.info("+++++++"+cfids);
        List<CartFurnitureEntity> cflist = new ArrayList<>();
        List<FurnitureEntity> furnitureList=new ArrayList<>();
        for(int i = 0; i < cfids.size(); i++) {
            log.info("====================");
            CartFurnitureEntity cf = cartFurnitureService.findFidAndAmount(cfids.get(i));

            log.info("dsffs : " + cf.getFid());
            FurnitureEntity furnitureEntity = furnitureService.readForCF(cf.getFid());
            furnitureList.add(furnitureEntity);
            log.info("sffd : " + furnitureEntity.getFid());
            furnitureService.updateStock(cf.getFid(), cf.getFurnitureAmount());
            cflist.add(cartFurnitureService.search(cfids.get(i)));
//            cartFurnitureService.delete(cfids.get(i));
        }
        model.addAttribute("furnitureList",furnitureList);
        model.addAttribute("orderInfo", orderInfoDTO);
        return "review/reviewpage";



//        for(int i = 0; i < cfids.size(); i++) {
//            Long nowCfid = cfids.get(i);
//            cartFurnitureService.selectFK(nowCfid);
//
//        }

//        List<Furniture> furnitures = new ArrayList<>();
//        for(int i = 0; i < rfids.size(); i++) {
//            Furniture f = furnitureService.read(rfids.get(i));
//            furnitures.add(f);
//        }
//
//        for (int i=0;i<cfids.size();i++){
//            Long cf=cfids.get(i);
//            cartFurnitureService.delete(cf);
//        }
//        model.addAttribute("furnitures", furnitures);
    }

//    @PostMapping("/order/{fid}")
//    public String OrderPayment(@PathVariable("fid")String fid,Order order,Model model){
//        Furniture furniture=new Furniture();
//        return "review/reviewpage";
//    }



//    @PostMapping("/insert")
//    public String insert(Model model, Long count, Long fid) {
//        log.info(count);
//        log.info(fid);
//        model.addAttribute("count", count);
//        model.addAttribute("fid", fid);
//        return "cart/test";
//    }


}

/*
    fid : 1 인 가구를 cart_furniture에 넣는다

    Furniture furniture = furnitureSerivce.read(1L);
    Cart_furniture cf = new Cart_Furntirue();
    name f.get
    price f.get
    amount detail ->
    subtotal p * a
    cf.setFurniture(furniture)
    save
    findByCartFurniture_Fid


 */