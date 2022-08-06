package com.social.moinda.api.member.dto;


import com.social.moinda.core.domains.member.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//  cannot deserialize from Object value (no delegate- or property-based Creator)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class SignupRequest {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "자기소개를 입력해주세요.")
    private String introduce;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String confirmPassword;

    public SignupRequest(String email,
                         String name,
                         String introduce,
                         String password,
                         String confirmPassword) {
        this.email = email;
        this.name = name;
        this.introduce = introduce;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Member bindEntity() {
        return new Member(
                this.email,
                this.name,
                this.introduce,
                this.password
        );
    }
}
