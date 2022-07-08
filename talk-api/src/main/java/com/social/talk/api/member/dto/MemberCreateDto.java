package com.social.talk.api.member.dto;


import com.social.talk.core.domains.member.entity.Member;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
public class MemberCreateDto {

    @Email
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Min(value = 4)
    private String password;

    @Min(value = 4)
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public boolean isMatchPassword() {
        return this.password.equals(this.confirmPassword);
    }

    public Member bindEntity() {
        return new Member(
                this.email,
                this.name,
                this.password
        );
    }
}
