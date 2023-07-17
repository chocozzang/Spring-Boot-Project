package com.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@ToString
@Getter
@Setter
public class NoticeFormDTO {
    private Long nid;
    private String noticeTitle;
    private String noticeContent;


    public void setNoticeDtoList(List<NoticeDTO> noticeDTOList) {
    }
}
