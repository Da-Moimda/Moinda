package com.social.moinda.core.domains.member.dto;

import com.social.moinda.core.domains.group.dto.GroupDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MemberDetails {

    /**
     *  TODO : Member에 자기소개(할말) 추가시 다른 객체를 사용할 것.
     *      MemberProfile - id, name, introduce, photo
     */
    private MemberDto memberDto;
    private List<GroupDto> groups;

    public MemberDetails(MemberDto memberDto, List<GroupDto> groups) {
        this.memberDto = memberDto;
        this.groups = groups;
    }
}
