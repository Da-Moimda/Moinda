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

    /**
     *  그룹명, 소개
     *  모임 목록 -> 모임날짜, 위치, 가격
     *  그룹 내 가입된 사용자 목록 -> 고유번호, 사용자 이름, 소개(없음),
     */
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
