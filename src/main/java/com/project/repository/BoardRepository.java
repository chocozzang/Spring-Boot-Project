package com.project.repository;

import com.project.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update board b set b.hits = b.hits + 1 where b.board_id = :bid", nativeQuery = true)
    void updateHits(@Param("bid") Long bid);

    List<BoardEntity> findAll(Sort sort);

    BoardEntity findByBid(Long bid);


    // JpaRepository에서 By 뒷 부분은 SQL의 where 조건 절에 해당된다.
    // 따라서, Containing을 붙여주면 Like 검색이 된다.
    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);


    List<BoardEntity> findByBoardTitleContaining(String keyword);
    List<BoardEntity> findTop2ByOrderByBoardHitsDesc();

    @Query(value = "select b.email from board b where b.board_id = :bid", nativeQuery = true)
    String getEmailByBid(Long bid);

    @Query(value = "select count(*) from board", nativeQuery = true)
    int getSZ();

    @Query(value = "select b.created_time from board b where b.board_id = :bid", nativeQuery = true)
    LocalDateTime findCRTByBid(Long bid);
}
