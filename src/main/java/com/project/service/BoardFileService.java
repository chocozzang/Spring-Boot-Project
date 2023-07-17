package com.project.service;

import com.project.entity.BoardFileEntity;

public interface BoardFileService {

    BoardFileEntity selectTopImg(Long bid);

    public String selectURL(Long bid);
}
