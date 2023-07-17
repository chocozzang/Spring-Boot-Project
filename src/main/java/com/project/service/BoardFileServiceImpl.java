package com.project.service;

import com.project.entity.BoardFileEntity;
import com.project.repository.BoardFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardFileServiceImpl implements BoardFileService{

    @Autowired
    BoardFileRepository boardFileRepository;

    public String selectURL(Long bid) {
        return boardFileRepository.getURLfromBid(bid);
    }

    @Override
    public BoardFileEntity selectTopImg(Long bid) {
        return boardFileRepository.selectFileImg(bid);
    }
}
