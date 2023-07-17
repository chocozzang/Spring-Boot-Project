package com.project.entity;

import com.project.dto.BoardDTO;
import com.project.service.MemberService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@Table(name = "board")
public class BoardEntity extends BaseEntity {

    @Id @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;  //게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column
    private String boardTitle;  //게시글 제목

    @Column(length = 500)
    private String boardContents;  //게시글 내용

    @Column(length = 20, nullable = false)
    private String boardWriter;  //작성자

    @Column(name = "hits")
    private int boardHits;  //조회 수

    @Column
    private int fileAttached;  //파일첨부

    private String email;  //이메일

    private String fileUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음.
        boardEntity.setEmail(boardDTO.getEmail());
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBid(boardDTO.getBid());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setEmail(boardDTO.getEmail());
        boardEntity.setFileUrl(boardDTO.getFileUrl());
        if(boardDTO.getBoardFile().isEmpty()) boardEntity.setFileAttached(0);
        else boardEntity.setFileAttached(1);

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.
        boardEntity.setEmail(boardDTO.getEmail());
        return boardEntity;
    }
}
