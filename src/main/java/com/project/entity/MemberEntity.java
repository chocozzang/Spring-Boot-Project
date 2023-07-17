package com.project.entity;

import com.project.constant.Role;
import com.project.dto.MemberFormDto;
import com.project.dto.MemberUpdDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@Repository
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long mid;

    @OneToOne(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private CartEntity cartEntity;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = true)
    private String birth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    List<BoardEntity> boardEntityList = new ArrayList<>();

    public static MemberEntity createMember(MemberFormDto memberFormDto,
                                            PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberFormDto.getName());
        memberEntity.setEmail(memberFormDto.getEmail());
        memberEntity.setAddress(memberFormDto.getAddress());
        memberEntity.setGender(memberFormDto.getGender());
        memberEntity.setTel(memberFormDto.getTel());
        memberEntity.setZipcode(memberFormDto.getZipcode());
        memberEntity.setBirth(memberFormDto.getBirth());
        String password = passwordEncoder.encode(memberFormDto.getPw());
        memberEntity.setPw(password);
        memberEntity.setRole(Role.USER);

        return memberEntity;
    }

    public static MemberEntity passwordchange(MemberFormDto memberFormDto,
                                            PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMid(memberEntity.getMid());
        memberEntity.setName(memberFormDto.getName());
        memberEntity.setEmail(memberFormDto.getEmail());
        memberEntity.setAddress(memberFormDto.getAddress());
        memberEntity.setGender(memberFormDto.getGender());
        memberEntity.setTel(memberFormDto.getTel());
        memberEntity.setZipcode(memberFormDto.getZipcode());
        memberEntity.setBirth(memberFormDto.getBirth());
        String password = passwordEncoder.encode(memberFormDto.getPw());
        memberEntity.setPw(password);
        memberEntity.setRole(Role.USER);

        return memberEntity;
    }


    public static MemberEntity toMemberEntity(MemberEntity memberEntityFormDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberEntityFormDto.getName());
        memberEntity.setTel(memberEntityFormDto.getTel());
        memberEntity.setAddress(memberEntityFormDto.getAddress());
        memberEntity.setZipcode(memberEntityFormDto.getZipcode());

        return memberEntity;
    }

    public static MemberEntity toMemberEntity(MemberUpdDTO memberUpdDTO) {
        ModelMapper modelMapper = new ModelMapper();
        MemberEntity memberEntity = modelMapper.map(memberUpdDTO, MemberEntity.class);
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberFormDto memberFormDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberFormDto.getName());
        memberEntity.setTel(memberFormDto.getTel());
        memberEntity.setAddress(memberFormDto.getAddress());
        memberEntity.setZipcode(memberFormDto.getZipcode());
        return memberEntity;
    }
}
