package com.social.moinda.core.domains.group.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupJoinResponse {
// ~~ 그룹에 가입되었습니다.
    // 그룹 번호, 그룹이름, 소개 ,정모 목록(모임시간, 위치, 금액), 사용자 목록(이름,자기소개)
    // 사용자의 번호,
    private Long memberId;
    private Long groupId;
    private String groupName;

    public GroupJoinResponse(Long memberId, Long groupId, String groupName) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
