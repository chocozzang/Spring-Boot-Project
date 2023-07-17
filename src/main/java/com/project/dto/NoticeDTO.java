package com.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NoticeDTO {
    private Long nid;
    private String noticeTitle;
    private String noticeContent;

    public static NoticeDTO toNoticeDTO(NoticeFormDTO noticeFormDTO) {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.nid = noticeFormDTO.getNid();
        noticeDTO.noticeTitle = noticeFormDTO.getNoticeTitle();
        noticeDTO.noticeContent = noticeFormDTO.getNoticeContent();

        return noticeDTO;
    }

}
