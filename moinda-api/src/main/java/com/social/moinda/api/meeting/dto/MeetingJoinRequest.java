package com.social.moinda.api.meeting.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingJoinRequest {

    @NotNull(message = "모임 번호가 필요합니다.")
    private Long meetingId;

    @NotNull(message = "사용자 번호가 필요합니다.")
    private Long memberId;

    @NotNull(message = "그룹 번호가 필요합니다.")
    private Long groupId;

    public MeetingJoinRequest(Long meetingId, Long memberId, Long groupId) {
        this.meetingId = meetingId;
        this.memberId = memberId;
        this.groupId = groupId;
    }

}
