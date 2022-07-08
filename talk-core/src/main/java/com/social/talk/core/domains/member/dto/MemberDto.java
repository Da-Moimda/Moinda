package com.social.talk.core.domains.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    @JsonIgnore
    private String password;

}
