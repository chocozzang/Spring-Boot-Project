package com.project.dto;

import com.project.constant.Role;
import com.project.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.RollbackException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberUpdDTO {

    private Long mid;
    private String name;
    private String email;
    private String pw;
    private String address;
    private String zipcode;
    private String tel;
    private String birth;
    private String gender;
    private Role role;
}



