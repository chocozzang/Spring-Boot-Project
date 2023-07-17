package com.project.service;

import com.project.dto.BoardDTO;
import com.project.entity.BoardEntity;
import com.project.entity.BoardFileEntity;
import com.project.repository.BoardFileRepository;
import com.project.repository.BoardRepository;
import com.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardEntity.setMemberEntity(memberRepository.findByMid(boardDTO.getMid()));
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            /*
                1.DTO에 담긴 파일을 꺼냄
                2.파일의 이름 가져옴
                3.서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4.저장 경로 설정
                5.해당 경로에 파일 저장
                6.board_table에 해당 데이터 save 처리
                7.board_file_table에 해당 데이터 save 처리
             */
            MultipartFile boardFile = boardDTO.getBoardFile();  //1
            String originalFilename = boardFile.getOriginalFilename();  //2
            String storedFileName = "/board/images/" + System.currentTimeMillis() + "_" + originalFilename;  //3
            String savePath = "C:/project_resources" + storedFileName;  //4
            boardFile.transferTo(new File(savePath));
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            boardEntity.setFileUrl(storedFileName);
            boardEntity.setMemberEntity(memberRepository.findByMid(boardDTO.getMid()));
            Long savedBid = boardRepository.save(boardEntity).getBid();
            BoardEntity board = boardRepository.findById(savedBid).get();
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board,
                    originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }

    }
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "bid"));
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long bid) {
        boardRepository.updateHits(bid);
    }

    @Transactional
    public BoardDTO findById(Long bid) {
//        Optional<BoardEntity> optionalBoardEntity = boardRepository.findByBid(bid);
        BoardEntity boardEntity = boardRepository.findByBid(bid);

        if (boardEntity != null) {
//            BoardEntity boardEntity = optionalBoardEntity.get();
            log.info("ser");
            LocalDateTime crt = boardRepository.findCRTByBid(bid);
            log.info(crt);
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            boardDTO.setBoardCreatedTime(crt);
            return boardDTO;
        } else {
            return null;
        }
    }

    // repository를 이용하여 title에 keyword 값이 포함된 데이터만 가져온다.
    // findByTitleContaining은 repository에 선언해줘야한다.
    // BoardDTO에 데이터를 매핑하여 리턴해준다.

    @Transactional
    public Page<BoardDTO> searchPosts(String keyword){
        Page<BoardEntity> boardEntities = boardRepository.findByBoardTitleContaining(keyword, PageRequest.of(0, 6, Sort.by("bid")));
        Page<BoardDTO> boardDTOPaging = boardEntities.map(board -> new BoardDTO(board.getBid(),
                board.getBoardWriter(), board.getBoardTitle(), board.getBoardContents(),
                board.getBoardHits(), board.getCreatedTime(), board.getEmail(),
                board.getUpdatedTime(), board.getMemberEntity(), board.getFileUrl(), board.getFileAttached()));

        return boardDTOPaging;
    }

    @Transactional
    public int getSZbySearching(String keyword){
        return boardRepository.findByBoardTitleContaining(keyword).size();
    }


    private BoardDTO convertEntityToDTO(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .bid(boardEntity.getBid())
                .boardWriter(boardEntity.getBoardWriter())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContents(boardEntity.getBoardContents())
                .boardHits(boardEntity.getBoardHits())
                .boardCreatedTime(boardEntity.getCreatedTime())
                .boardUpdatedTime(boardEntity.getUpdatedTime())
                .build();
    }

    @Modifying
    @javax.transaction.Transactional
    public Long update(BoardDTO boardDTO) throws IOException {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        log.info(boardEntity);
        boardEntity.setMemberEntity(memberRepository.findByMid(boardDTO.getMid()));
        log.info("test");
        log.info("2306291426");
        if(boardRepository.findById(boardDTO.getBid()).get().getFileAttached() == 0) {
            if (boardEntity.getFileAttached() == 1) {
                MultipartFile boardFile = boardDTO.getBoardFile();
                String originalFilename = boardFile.getOriginalFilename();
                String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "C:/project_resources/board/" + storedFilename;
                boardFile.transferTo(new File(savePath));
                BoardEntity checkBoard = boardRepository.findById(boardDTO.getBid()).get();
                boardEntity.setFileUrl(storedFilename);
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(checkBoard, originalFilename, storedFilename);
                boardFileRepository.save(boardFileEntity);
            }
        } else {

            log.info("xx");
            log.info(boardEntity.getFileAttached());
            log.info("yy");

            if (boardEntity.getFileAttached() == 1) {
                Long findBid = boardDTO.getBid();
                MultipartFile boardFile = boardDTO.getBoardFile();
                String attachedOriginalFileName = boardFile.getOriginalFilename();

                String storedOriginalFileName = boardFileRepository.findOriginNameByBid(findBid);

                if (!(attachedOriginalFileName.equals(storedOriginalFileName))) {
                    String newStoredFilename = System.currentTimeMillis() + "_" + attachedOriginalFileName;
                    String storedFileName = boardFileRepository.findStoredNameByBid(findBid);
                    String deletePath = "C:/project_resources/board/" + storedFileName;
                    boardFileRepository.myFileDelete(boardDTO.getBid());
                    Path deletePathNew = Paths.get(deletePath);
                    Files.delete(deletePathNew);
                    String savePath = "C:/project_resources/board/" + newStoredFilename;
                    boardFile.transferTo(new File(savePath));
                    BoardEntity checkBoard = boardRepository.findById(boardDTO.getBid()).get();
                    BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(checkBoard, attachedOriginalFileName, newStoredFilename);
                    boardEntity.setFileUrl(newStoredFilename);
                    boardFileRepository.save(boardFileEntity);
                }
            }

            else {
                boardEntity.setFileAttached(1);
            }

            log.info("xyzx");
            log.info("xzyx");
        }
        boardRepository.save(boardEntity);

//        log.info(findById(boardDTO.getBid()));
//        return findById(boardDTO.getBid());
        return boardDTO.getBid();
    }

    public BoardDTO search(Long bid) {
        BoardDTO boardDTO = BoardDTO.toBoardDTO(boardRepository.findById(bid).get());
        return boardDTO;
    }

    public void delete(Long bid) {
        boardRepository.deleteById(bid);
    }

    public String findOriginalFileName(Long bid) {
        return boardFileRepository.findOriginNameByBid(bid);
    }

    public String findStoredlFileName(Long bid) {
        return boardFileRepository.findStoredNameByBid(bid);
    }


    public Page<BoardDTO> paging(Pageable pageable) {
        log.info("xtasddsfasdfasdfewr");
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 6; // 한 페이지에 보여줄 글 갯수 3 * 2 형태로 나타낼 것임
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bid")));
        log.info("xyz12344567+_+_+_+_+_+_+_");
//        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
//        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
//        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
//        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
//        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
//        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
//        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
//        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부
        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getBid(),
                board.getBoardWriter(), board.getBoardTitle(),
                board.getBoardContents(), board.getBoardHits(),
                board.getCreatedTime(), board.getEmail(),
                board.getUpdatedTime(),
                board.getMemberEntity(), board.getFileUrl(),
                board.getFileAttached()));
        log.info("zxasdfas---------------------------");
        return boardDTOS;

    }

    public List<BoardEntity> selectTopTwo() {
        return boardRepository.findTop2ByOrderByBoardHitsDesc();
    }

    public String getEmailByBid(Long bid) {
        return boardRepository.getEmailByBid(bid);
    }


    public int getSZ() {return boardRepository.getSZ();}
}
