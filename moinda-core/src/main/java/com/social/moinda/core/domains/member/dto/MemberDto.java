package com.social.moinda.core.domains.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    @JsonIgnore
    private String password;

}
