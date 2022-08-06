package com.social.moinda.core.domains.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class SignupResponse {

    private Long memberId;
    private String email;
    private String name;
    private String introduce;
    @JsonIgnore
    private String password;

    public SignupResponse(Long memberId,
                          String email,
                          String name,
                          String introduce,
                          String password) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.introduce = introduce;
        this.password = password;
    }
}
