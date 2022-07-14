package com.social.moinda.core.domains.group.dto;


import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class GroupCreateResponse {

    private Long groupId;

    private String name;
    private String introduce;
    // TODO : 네이밍 변경 필요
    private String locationDo;
    private String locationSi;
    private String concern;
    private int capacity;

    private MemberDto memberDto;

}
