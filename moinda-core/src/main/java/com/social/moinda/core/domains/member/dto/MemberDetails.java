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
    // memberId 와 group목록을 갖는 DTO를 하나 만든다.
    // TODO : findMemberWithRegisteredGroups 를 써서 전달시 쓰는중.
    private Long memberId;
    private List<GroupDto> groups;

    public MemberDetails(Long memberId, List<GroupDto> groups) {
        this.memberId = memberId;
        this.groups = groups;
    }
}
