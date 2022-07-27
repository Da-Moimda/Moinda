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
    private Long memberId;
    private List<GroupDto> groups;

    public MemberDetails(Long memberId, List<GroupDto> groups) {
        this.memberId = memberId;
        this.groups = groups;
    }
}
