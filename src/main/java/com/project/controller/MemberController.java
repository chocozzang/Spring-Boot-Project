package com.project.controller;

import com.project.dto.MemberFormDto;
import com.project.dto.MemberUpdDTO;
import com.project.entity.CartEntity;
import com.project.entity.MemberEntity;
import com.project.service.CartService;
import com.project.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CartService cartService;

    @GetMapping("/success")
    public String main() {return "redirect:/main/";}

    @GetMapping("/new")
    public String memberForm(Model model) {
        log.info("member new getmapping");
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @Transactional
    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {
          // 에러가 있으면 회원가입 페이지로 이동
        if (bindingResult.hasErrors()) {
            log.info(memberFormDto);
            return "member/memberForm";
        }
        try {
            String nowEmail = memberFormDto.getEmail();
            int chkEmail = memberService.isanyusers(nowEmail);

            if(chkEmail > 0) {
                model.addAttribute("errormessage", "이미 있는 이메일입니다.");
                return "member/memberForm";
            }

            MemberEntity memberEntity = MemberEntity.createMember(memberFormDto, passwordEncoder);
            CartEntity cartEntity = new CartEntity();
            cartEntity.setMemberEntity(memberEntity);
            // 회원정보 DB에 저장
            memberService.saveMember(memberEntity);
            cartService.makeCart(cartEntity);
        } catch (IllegalStateException e) {
            // 중복 회원일 경우 처리
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        // 문제 없으면 main페이지로 이동
        return "redirect:/members/login";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @PostMapping("/login")
    public String loginMemberPost() {
        log.info("loginpost");
        return "/projecttest/mainpage";
    }

    @GetMapping("/myinfo")
    public String logininfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity memberEntity = memberService.getMemberByEmail(username);
        model.addAttribute("member", memberEntity);
        return "member/memberDetail";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg",
                "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }

    @PostMapping("/modify")
    public String modify(MemberUpdDTO memberUpdDTO, Model model) {

        log.info(memberUpdDTO);

        model.addAttribute("updateMember", memberUpdDTO);

        return "member/memberUpdateForm";
    }

    @PostMapping("/apply")
    public String modify(MemberUpdDTO memberUpdDTO) {
        memberService.updateMember(memberUpdDTO);
        return "redirect:/furniture/list";
    }

    @PostMapping("/remove")
    public String remove(Long mid) {
        memberService.removeMember(mid);
        return "redirect:/members/logout";
    }

    @GetMapping("/passwordChange")
    public String pwchg(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity memberEntity = memberService.getMemberByEmail(username);
        model.addAttribute("member", memberEntity);
        return "member/memberPasswordChange";
    }

    @PostMapping("/passwordChange")
    public String pwchg(Model model, MemberUpdDTO memberUpdDTO) {
        String password = passwordEncoder.encode(memberUpdDTO.getPw());
        memberUpdDTO.setPw(password);
        memberService.updateMember(memberUpdDTO);
        return "redirect:/furniture/list";
    }
}








