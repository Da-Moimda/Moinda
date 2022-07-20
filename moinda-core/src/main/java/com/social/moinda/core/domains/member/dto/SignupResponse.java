package com.social.moinda.core.domains.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class SignupResponse {

    private Long memberId;
    private String email;
    private String name;
    @JsonIgnore
    private String password;
}
