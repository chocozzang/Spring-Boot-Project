package com.project.service;

import com.project.dto.NoticeDTO;
import com.project.dto.NoticeFormDTO;
import com.project.entity.NoticeEntity;
import com.project.repository.NoticeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional
public class NoticeService {

  /*  @Autowired
    NoticeRepository noticeRepository;*/

@Autowired
    NoticeRepository noticeRepository;
    //전체조회
    //전체리턴-JPA findAll사용
    public List<NoticeEntity> getAllNoticeList(){

        return noticeRepository.findAll();
    }

    //상세조회
    //nid 반환
    public NoticeEntity getNotice(Long nid){
        return noticeRepository.findById(nid).get();
    }

    //등록
    public void noticeWrite(NoticeDTO noticeDTO){
        NoticeEntity notice = NoticeEntity.toNotice(noticeDTO);
        log.info(notice);
        noticeRepository.save(notice);
    }

    //수정
    public void noticeUpdate(NoticeFormDTO noticeFormDTO) {
        NoticeEntity noticeEntity = noticeRepository.findById(noticeFormDTO.getNid()).get();
        noticeEntity.setNoticeTitle(noticeFormDTO.getNoticeTitle());
        noticeEntity.setNoticeContent(noticeFormDTO.getNoticeContent());

        noticeRepository.save(noticeEntity);
    }


    //삭제
    public void noticeDelete(Long nid){
        if(noticeRepository.findById(nid).orElse(null) != null){
            noticeRepository.deleteById(nid);
        }
    }
}
