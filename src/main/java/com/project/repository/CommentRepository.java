package com.project.repository;

import com.project.entity.BoardEntity;
import com.project.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByBidDesc(BoardEntity boardEntity);
}
