package com.social.moinda.core.domains.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    private String introduce;

    public MemberDto(Long memberId, String email, String name, String introduce) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.introduce = introduce;
    }
}
