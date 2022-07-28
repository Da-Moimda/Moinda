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

    public MemberDto(Long memberId, String email, String name) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
    }
}
