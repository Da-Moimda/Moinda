package com.social.moinda.core.domains.group.dto;

import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GroupDetails {

    private Long groupId;
    private String groupName;
    private String groupIntroduce;

    private List<MemberDto> members;
    private List<MeetingDto> meetings;

    public GroupDetails(Long groupId,
                        String groupName,
                        String groupIntroduce,
                        List<MemberDto> members,
                        List<MeetingDto> meetings) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupIntroduce = groupIntroduce;
        this.members = members;
        this.meetings = meetings;
    }
}
