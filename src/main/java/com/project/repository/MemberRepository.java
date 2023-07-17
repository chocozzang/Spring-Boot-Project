package com.project.repository;

import com.project.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 중복된 회원 검색
    MemberEntity findByEmail(String email);
    MemberEntity findByMid(Long mid);


    @Query(value = "select m.role from member m where m.email = :email", nativeQuery = true)
    String getRoleByEmail(String email);

    @Query(value = "select COUNT(*) from member m where m.email = :email", nativeQuery = true)
    int emailchk(String email);

    @Query(value = "select * from member m where m.role = 'USER'", nativeQuery = true)
    List<MemberEntity> findAll();
}
