package com.social.moinda.api.group.service;

import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.core.domains.group.dto.GroupDetails;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupQueryService {

    private final GroupQueryRepository groupQueryRepository;
    private final MeetingQueryRepository meetingQueryRepository;

    public List<GroupDto> searchGroups() {
        return groupQueryRepository.findGroups();
    }

    public List<GroupDto> searchGroups(String search) {
        return groupQueryRepository.findAllByNameContains(search);
    }

    public GroupDetails getGroupDetails(Long groupId) {
        Group group = groupQueryRepository.findById(groupId)
                .orElseThrow(NotFoundGroupException::new);

        List<MeetingDto> meetings = meetingQueryRepository.findMeetingsByGroupId(groupId);

        return group.bindToGroupDetails(meetings);
    }
}
