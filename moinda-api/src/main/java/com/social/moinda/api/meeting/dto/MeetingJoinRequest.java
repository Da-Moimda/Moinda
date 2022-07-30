package com.social.moinda.api.meeting.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingJoinRequest {

    private Long meetingId;
    private Long memberId;
    private Long groupId;

    public MeetingJoinRequest(Long meetingId, Long memberId, Long groupId) {
        this.meetingId = meetingId;
        this.memberId = memberId;
        this.groupId = groupId;
    }

}
