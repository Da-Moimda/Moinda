package com.social.moinda.api.meeting.service;

import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.groupmember.exception.NotJoinedGroupMemberException;
import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.dto.MeetingJoinRequest;
import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
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
    private final GroupQueryRepository groupQueryRepository;
    private final GroupMemberQueryRepository groupMemberQueryRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final MeetingQueryRepository meetingQueryRepository;

    public MeetingCreateResponse create(MeetingCreateDto meetingCreateDto) {
        // firstResult/maxResults specified with collection fetch; applying in memory!
        Group group = existGroup(meetingCreateDto.getGroupId());

        Meeting meeting = meetingRepository.save(meetingCreateDto.bindToEntity(group));

        return meeting.bindToCreateResponse();
    }

    // TODO : 반환값 설정 필요한지 고민
    public void joinMeeting(MeetingJoinRequest joinRequest) {
        // TODO : 각 Id 마다 다른 예외메세지가 발생해야 한다.
        GroupMember groupMember = groupMemberQueryRepository.findGroupMemberById(joinRequest.getGroupId(), joinRequest.getMemberId())
                .orElseThrow(NotJoinedGroupMemberException::new);

        Meeting meeting = meetingQueryRepository.findById(joinRequest.getMeetingId())
                .orElseThrow(NotFoundMeetingException::new);

        MeetingMember meetingMember = new MeetingMember(meeting, groupMember.getMember());
        // TODO : 참가상태 확인필요,
        meetingMemberRepository.save(meetingMember);
    }

    @Transactional(readOnly = true)
    public Group existGroup(Long groupId) {
        return groupQueryRepository.findById(groupId)
                .orElseThrow(NotFoundGroupException::new);
    }
}
