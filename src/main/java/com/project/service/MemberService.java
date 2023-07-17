package com.project.service;

import com.project.dto.MemberFormDto;
import com.project.dto.MemberUpdDTO;
import com.project.entity.MemberEntity;
import com.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    // 1. @RequiredArgsConstructor를 통한 빈을 주입
    // (final, @NotNull 붙은 항목에 생성자를 생성해줌)
    // 2. Setter 사용
    // 3. @Autowired
    private final MemberRepository memberRepository;

    public void removeMember(Long mid) {
        memberRepository.deleteById(mid);
    }

    public MemberEntity saveMember(MemberEntity memberEntity) {
        log.info(memberEntity);
        validateDuplicateMember(memberEntity);
        return memberRepository.save(memberEntity);
    }

    // 회원 중복 check
    private void validateDuplicateMember(MemberEntity memberEntity) {
        MemberEntity findMember = memberRepository.findByEmail(memberEntity.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        // User 객체 반환
        // 회원의 이메일, 비밀번호, role을 파라미터 값으로 넘겨줌
        return User.builder()
                .username(member.getEmail())
                .password(member.getPw())
                .roles(member.getRole().toString())
                .build();
    }

    public MemberEntity getMemberByEmail(String username) {
        return memberRepository.findByEmail(username);
    }


    public List<MemberFormDto> findAll() {
        List<MemberEntity> memberList = memberRepository.findAll();
        List<MemberFormDto> memberFormDtoList = new ArrayList<>();
        for (MemberEntity member : memberList) {
            memberFormDtoList.add(MemberFormDto.toMemberDTO(member));
        }
        return memberFormDtoList;
    }

    public MemberFormDto findById(Long id) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
//            Member member = optionalMember.get();
//            MemberFormDto memberFormDto = MemberFormDto.toMemberDTO(member);
//            return memberDTO;
            return MemberFormDto.toMemberDTO(optionalMember.get());
        } else {
            return null;
        }

    }

    public MemberEntity update(String myEmail) {
       Optional<MemberEntity> optionalMember = Optional.ofNullable(memberRepository.findByEmail(myEmail));
       if (optionalMember.isPresent()){
            return MemberEntity.toMemberEntity(optionalMember.get());
        }else {
           return null;
       }
    }

    public void updateMember(MemberUpdDTO memberUpdDTO) {

        MemberEntity upMemberEntity = MemberEntity.toMemberEntity(memberUpdDTO);
        memberRepository.save(upMemberEntity);

    }

    public String getRoleByEmail(String email) {
        return memberRepository.getRoleByEmail(email);
    }

    public int isanyusers(String email) {
        return memberRepository.emailchk(email);
    }

    public List<MemberEntity> getlist() {return memberRepository.findAll();}

    public void delete(Long mid) {memberRepository.deleteById(mid);}
}


