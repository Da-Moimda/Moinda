package com.social.moinda.api.meeting.service;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingCommandService {

    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;

    public Meeting create(Meeting meeting) {

        Meeting entity = meetingRepository.save(meeting);

        return entity;
    }

    public MeetingCreateResponse create(MeetingCreateDto meetingCreateDto) {

        Group group = existGroup(meetingCreateDto.getGroupId());

        Meeting meeting = meetingRepository.save(meetingCreateDto.bindToEntity(group));

        return meeting.bindToCreateResponse();
    }

    @Transactional(readOnly = true)
    public Group existGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("없는 그룹입니다."));
    }
}
