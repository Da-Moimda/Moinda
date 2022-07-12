package com.social.moinda.api.member.dto;


import com.social.moinda.core.domains.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//  cannot deserialize from Object value (no delegate- or property-based Creator)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberCreateDto {

    @Email
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String confirmPassword;

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
