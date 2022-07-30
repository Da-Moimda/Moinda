package com.social.moinda.api.group.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupJoinRequest {

    @NotNull(message = "그룹 번호가 필요합니다.")
    private Long groupId;

    @NotNull(message = "사용자 번호가 필요합니다.")
    private Long memberId;

    public GroupJoinRequest(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }
}
