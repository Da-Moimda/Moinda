package com.social.moinda.core.domains.meeting.dto;


import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MeetingDetails {
    private List<MemberDto> members;

    private int userNum;

    private LocalDateTime meetingDate;

    public MeetingDetails(List<MemberDto> members, int userNum, LocalDateTime meetingDate) {
        this.members = members;
        this.userNum = userNum;
        this.meetingDate = meetingDate;
    }
}
