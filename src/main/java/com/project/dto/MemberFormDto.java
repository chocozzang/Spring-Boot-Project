package com.project.dto;

import com.project.constant.Role;
import com.project.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberFormDto {
    // @NotBlank - NULL체크 + 문자열 길이 0인지 검사 + 빈 문자열("")인지 검사
    // @NotEmpty - NULL체크 + 문자열 길이 0인지 검사
    // @Null - 값이 NULL인지 검사
    // @NotNull - 값이 NULL이 아닌지 검사

    private Long mid;
    private Role role;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String pw;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String tel;

    @NotBlank(message = "생년월일은 필수 입력 값입니다.")
    private String birth;

    @NotEmpty(message = "성별은 필수 입력 값입니다.")
    private String gender;

    public static MemberFormDto toMemberDTO(MemberEntity memberEntity) {
        MemberEntity memberEntityDTO = new MemberEntity();
        memberEntityDTO.setName(memberEntity.getName());
        memberEntityDTO.setTel(memberEntity.getTel());
        memberEntityDTO.setPw(memberEntity.getPw());
        memberEntityDTO.setMid(memberEntityDTO.getMid());
        return null;
    }
}



