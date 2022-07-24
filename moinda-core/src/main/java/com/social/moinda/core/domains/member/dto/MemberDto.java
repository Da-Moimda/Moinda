package com.social.moinda.core.domains.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;

}
