package com.project.controller;

import com.project.dto.NoticeDTO;
import com.project.dto.NoticeFormDTO;
import com.project.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(value="/notice")
public class NoticeController {

@Autowired
NoticeService noticeService;

public void createNotice() {
   NoticeDTO notice = new NoticeDTO();
   for(int i =  1; i<=10; i++){
       notice.setNoticeTitle("noticeTitle"+i);
       notice.setNoticeContent("content"+i);
       noticeService.noticeWrite(notice);
   }
}
    @GetMapping("/noticeList")
    public void getAllNoticeList(Model model){
        model.addAttribute("noticeList",noticeService.getAllNoticeList());
    }
    //전체읽기
    @GetMapping({"/noticeRead","/noticeModify"})
    public void getNotice(Long nid,Model model){
    model.addAttribute("notice",noticeService.getNotice(nid));

    }

    @GetMapping("/noticeWrite")
    public String writerForm(@ModelAttribute NoticeFormDTO noticeFormDTO){

        return "notice/noticeWrite";
    }

    @PostMapping("/noticeWrite")
    public String noticeWrite(@ModelAttribute NoticeFormDTO noticeFormDTO){

        log.info(noticeFormDTO);

        NoticeDTO noticeDTO = NoticeDTO.toNoticeDTO(noticeFormDTO);

        log.info(noticeDTO);

        noticeService.noticeWrite(noticeDTO);

        return "redirect:/notice/noticeList";

    }

    @PostMapping("/noticeModify")
    public String noticeModify(NoticeFormDTO noticeFormDTO){
        noticeService.noticeUpdate(noticeFormDTO);
        return "redirect:/notice/noticeList";
    }

   /* @GetMapping("/noticeModify")
    public String noticeModify(Long nid,Model model){
        model.addAttribute("notice",noticeService.getNotice(nid));
        return "notice/noticeModify";

    }*/


    //조회없이 삭제
    @PostMapping("/noticeRemove")
    public String noticeDelete(Long nid) {
        noticeService.noticeDelete(nid);
        return "redirect:/notice/noticeList";
    }


}
