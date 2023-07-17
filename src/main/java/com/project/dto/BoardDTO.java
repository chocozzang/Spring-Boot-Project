package com.project.dto;

import com.project.entity.BoardEntity;
import com.project.entity.MemberEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor  // 기본생성자
@AllArgsConstructor  // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long bid;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private String boardWriter;
    private int boardHits;
    private String email;
    private String name;
    private Long mid;
    private String fileUrl;

    private MultipartFile boardFile;  // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName;  // 서버 저장용 파일 이름
    private int fileAttached;  //파일 첨부 여부(첨부 1, 미첨부 0)
    private String createdTimeString;

    public BoardDTO(Long bid, String boardWriter, String boardTitle, String boardContents,
                    int boardHits, LocalDateTime createdTime, String email, LocalDateTime updatedTime,
                    MemberEntity memberEntity, String fileUrl, int fileAttached) {
        this.bid = bid;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardWriter = boardWriter;
        this.boardCreatedTime = createdTime;
        this.boardHits = boardHits;
        this.email = email;
        this.boardUpdatedTime = updatedTime;
        this.mid = memberEntity.getMid();
        this.fileUrl = fileUrl;
        this.fileAttached = fileAttached;
        this.createdTimeString = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBid(boardEntity.getBid());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setEmail(boardEntity.getEmail());
        boardDTO.setMid(boardEntity.getMemberEntity().getMid());
        boardDTO.setFileUrl(boardEntity.getFileUrl());
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            // orginalFileName, storedFileName : board_file_table(BoardFileEntity)
            // join
            // select * from board_table b, board_file_table bf where b.id=bf.board_id
            // and where b.id=?

            //boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            //boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }

        return boardDTO;
    }
}
