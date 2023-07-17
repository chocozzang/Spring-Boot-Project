package com.project.repository;

import com.project.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

    @Query(value = "select f.original_file_name from board_file f where f.board_id = :bid", nativeQuery = true)
    String findOriginNameByBid(Long bid);

    @Query(value ="select f.stored_file_name from board_file f where f.board_id = :bid", nativeQuery = true)
    String findStoredNameByBid(Long bid);

    @Modifying
    @Query(value = "delete from board_file f where f.board_id = :bid", nativeQuery = true)
    void myFileDelete(Long bid);

    @Query(value = "select * from board_file f where f.board_id = :bid", nativeQuery = true)
    BoardFileEntity selectFileImg(Long bid);

    @Query(value = "select bf.stored_file_name from board_file bf where bf.board_id = :bid", nativeQuery = true)
    String getURLfromBid(Long bid);

}
