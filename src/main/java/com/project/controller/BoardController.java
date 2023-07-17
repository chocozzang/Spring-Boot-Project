package com.project.controller;

import com.project.dto.BoardDTO;
import com.project.dto.CommentDTO;
import com.project.entity.MemberEntity;
import com.project.service.BoardService;
import com.project.service.CommentService;
import com.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        MemberEntity writeMember = memberService.getMemberByEmail(username);
        model.addAttribute("email", username);
        model.addAttribute("writer", writeMember.getName());

        return "board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDto = " + boardDTO);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity writeMember = memberService.getMemberByEmail(username);
        boardDTO.setMid(writeMember.getMid());
        boardDTO.setEmail(writeMember.getEmail());
        boardDTO.setName(writeMember.getName());
        log.info("xxxxxx");
        boardService.save(boardDTO);
        log.info("yyyyyyy");
        return "redirect:/board/paging";
    }



    @GetMapping("/{bid}")
    public String findById(@PathVariable Long bid, Model model, @PageableDefault(page=1) Pageable pageable){
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        log.info("1345;kasdjf;laksdfja;sf");
        boardService.updateHits(bid);
        BoardDTO boardDTO = boardService.findById(bid);
        boardDTO.setOriginalFileName(boardService.findOriginalFileName(bid));
        boardDTO.setStoredFileName(boardService.findStoredlFileName(bid));
//        String realurl = "/board/" + boardDTO.getStoredFileName();
        /* 댓글 목록 가져오기 */
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity writeMember = memberService.getMemberByEmail(username);
        List<CommentDTO> commentDTOList = commentService.findAll(bid);
        model.addAttribute("nowwriter", writeMember.getName());
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("fileurl", boardDTO.getStoredFileName());
        model.addAttribute("page", pageable.getPageNumber());
        return "board/detail";
    }

    @GetMapping("")
    public String findByBid(Long bid, Model model, @PageableDefault(page=1) Pageable pageable, String errormessage){
        log.info("sadfasdfasdjflkasjdflkasdjfl;kasdjf;laksdfja;sf");
        boardService.updateHits(bid);
        BoardDTO boardDTO = boardService.findById(bid);
        boardDTO.setOriginalFileName(boardService.findOriginalFileName(bid));
        boardDTO.setStoredFileName(boardService.findStoredlFileName(bid));
//        String realurl = "/board/" + boardDTO.getStoredFileName();
        /* 댓글 목록 가져오기 */

        /* 여기 네줄 추가 */
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        MemberEntity writeMember = memberService.getMemberByEmail(username);
        model.addAttribute("nowwriter", writeMember.getName());
        /* 추가 추가 추가 */

        List<CommentDTO> commentDTOList = commentService.findAll(bid);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("fileurl", boardDTO.getStoredFileName());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("errormessage", errormessage);
        return "board/detail";
    }

    @GetMapping("/update/{bid}")
    public String updateForm(@PathVariable Long bid, Model model, @PageableDefault(page = 1) Pageable pageable , RedirectAttributes redirectAttributes){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        String nowBoardName = boardService.getEmailByBid(bid);
        MemberEntity memberEntity = memberService.getMemberByEmail(username);


        if((username.equals(nowBoardName)) || (memberEntity.getRole().equals("ADMIN"))) {
            BoardDTO boardDTO = boardService.findById(bid);
            boardDTO.setOriginalFileName(boardService.findOriginalFileName(bid));
            boardDTO.setStoredFileName(boardService.findStoredlFileName(bid));
            boardDTO.setMid(memberEntity.getMid());
            model.addAttribute("boardUpdate", boardDTO);
            model.addAttribute("page", pageable.getPageNumber());
            return "board/update";
        }

        else {
            redirectAttributes.addAttribute("errormessage", "다른 이용자의 게시글입니다.");
            return "redirect:/board?bid=" + bid;
        }

    }

     @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model, @RequestParam("pg") int pagenum) throws IOException{
//        BoardDTO board = boardService.update(boardDTO);
        Long bid = boardService.update(boardDTO);
        BoardDTO afterBDTO = boardService.findById(bid);
        afterBDTO.setOriginalFileName(boardService.findOriginalFileName(bid));
        afterBDTO.setStoredFileName(boardService.findStoredlFileName(bid));
        model.addAttribute("board", afterBDTO);
        model.addAttribute("page", pagenum);
        return "board/detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/delete/{bid}")
    public String delete(@PathVariable Long bid, Model model, @PageableDefault(page = 1) Pageable pageable , RedirectAttributes redirectAttributes){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        String nowBoardName = boardService.getEmailByBid(bid);
        String userRole = memberService.getRoleByEmail(username);


        if((username.equals(nowBoardName)) || (userRole.equals("ADMIN"))) {
            boardService.delete(bid);
            return "redirect:/board/paging";
        }
        else {
            redirectAttributes.addAttribute("errormessage", "다른 이용자의 게시글입니다.");
            return "redirect:/board?bid=" + bid;
        }


    }

//    @GetMapping("/")
//    public String findAll(Model model){
//        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
//        List<BoardDTO> boardDTOList = boardService.findAll();
//        model.addAttribute("boardList", boardDTOList);
//        return "redirect:/board/paging";
//    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
//        pageable.getPageNumber();
        log.info("xxyxyyxyx");
        Page<BoardDTO> boardList = boardService.paging(pageable);
        log.info("yyxyxyxyxy");

        // int startPage = 1;
        // int endPage = Math.max(pageable.getTotalPages(), 1);
        //int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) -1) * blockLimit + 1; // 1 4 7 10 ~~
        //int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit -1 : boardList.getTotalPages();

        int blockLimit = 6;
        int totalsize = boardService.getSZ();
        //endPage 값이 0일때(아무 게시글도 없을 때) 1로 설정
        int startPage = 1;
        int endPage = Math.max((int) (Math.ceil((double)totalsize / blockLimit)), 1);
        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개
        model.addAttribute("boardList", boardList.getContent());
        model.addAttribute("start_page", startPage);
        model.addAttribute("end_page", endPage);
        return "board/board_list";
    }

    @PostMapping("/paging")
    public String pagingpost(@PageableDefault(page = 1) Pageable pageable, Model model){
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        // int startPage = 1;
        // int endPage = Math.max(pageable.getTotalPages(), 1);
        int blockLimit = 6;
        int totalsize = boardService.getSZ();
        //endPage 값이 0일때(아무 게시글도 없을 때) 1로 설정
        int startPage = 1;
        int endPage = Math.max((int) (Math.ceil((double)totalsize / blockLimit)), 1);
        model.addAttribute("boardList", boardList);
        model.addAttribute("start_page", startPage);
        model.addAttribute("end_page", endPage);
        return "board/board_list";
    }

    // @RequestParam을 이용하여 요청으로 들어온 값 중 'keyword'로 넘어온 값을 받아서 해당 키워드가
    // 포함된 리스트를 넘겨주는 searchPosts를 사용하고 model을 통해 view에 값을 넘겨준다.
    // searchPosts는 Service에서 구현해준다.

    @GetMapping("/search")
    public String search(@PageableDefault(page = 1) Pageable pageable,
                         @RequestParam(value = "keyword") String keyword, Model model){
        Page<BoardDTO> boardDTOList = boardService.searchPosts(keyword);
        int blockLimit = 6;
        int totalsize = boardService.getSZbySearching(keyword);
        //endPage 값이 0일때(아무 게시글도 없을 때) 1로 설정
        int startPage = 1;
        int endPage = Math.max((int) (Math.ceil((double)totalsize / blockLimit)), 1);
        model.addAttribute("boardList", boardDTOList.getContent());
        model.addAttribute("start_page", startPage);
        model.addAttribute("end_page", endPage);
        model.addAttribute("boardSearchTitle", keyword);

        return "board/board_list";
    }

}
