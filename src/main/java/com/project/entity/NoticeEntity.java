package com.project.entity;

import com.project.dto.NoticeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="notice")
@Repository
public class NoticeEntity extends BaseEntity {

    // 공지사항 게시글 번호
    // 기본키, 이름은 notice_id
    // Auto Increment(만들 때마다 1씩 증가함)
    @Id @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;

    // 공지사항 제목
    // Not Null 설정
    @Column(nullable = false, name = "notice_title")
    private String noticeTitle;

    // 공지사항 내용
    // Not Null 설정
    @Column(nullable = false, name = "notice_content")
    private String noticeContent;

    // 공지사항은 만들어둔 다음에,
    // 본인 방 자랑 게시판에 붙여도 되고,
    // 메인 페이지에 넣어도 될 것 같음
    // 아직 위치는 안정함

    public static NoticeEntity toNotice(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNid(noticeDTO.getNid());
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeContent(noticeDTO.getNoticeContent());
        return noticeEntity;
    }
}
