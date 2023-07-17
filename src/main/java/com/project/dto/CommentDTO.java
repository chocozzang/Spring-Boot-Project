package com.project.dto;

import com.project.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class CommentDTO {

    private Long bid;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;
    private String commentStringTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBid(commentEntity.getBid());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentStringTime(commentEntity.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
