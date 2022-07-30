package com.social.moinda.api.meeting.service;

import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.dto.MeetingJoinRequest;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberQueryRepository;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import com.social.moinda.core.domains.meeting.entity.MeetingRepository;
import com.social.moinda.core.domains.meetingmember.entity.MeetingMember;
import com.social.moinda.core.domains.meetingmember.entity.MeetingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingCommandService {

    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberQueryRepository groupMemberQueryRepository;

    private final MeetingMemberRepository meetingMemberRepository;

    private final MeetingQueryRepository meetingQueryRepository;

    public MeetingCreateResponse create(MeetingCreateDto meetingCreateDto) {

        Group group = existGroup(meetingCreateDto.getGroupId());

        Meeting meeting = meetingRepository.save(meetingCreateDto.bindToEntity(group));

        return meeting.bindToCreateResponse();
    }

    @Transactional(readOnly = true)
    public Group existGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(NotFoundGroupException::new);
    }

    public void joinMeeting(MeetingJoinRequest joinRequest) {

        GroupMember groupMember = groupMemberQueryRepository.findGroupMemberById(joinRequest.getGroupId(), joinRequest.getMemberId())
                .orElseThrow(() -> new IllegalStateException("에러발생..."));

        System.out.println("groupMember : " + groupMember);

        // TODO : N+1 -> Querydsl
        Meeting meeting = meetingQueryRepository.findById(joinRequest.getMeetingId())
                .orElseThrow(() -> new IllegalStateException("없는 모임입니다."));

        System.out.println("meeting : " + meeting);

        MeetingMember meetingMember = new MeetingMember(meeting, groupMember.getMember());
        System.out.println("meetingMember : " + meetingMember);
        meetingMemberRepository.save(meetingMember);

    }
}
