package com.social.moinda.api.meeting.service;

import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQueryService {

    private final MeetingQueryRepository meetingQueryRepository;

    public MeetingDetails getMeetingDetails(Long meetingId) {
        Meeting meeting = meetingQueryRepository.findMeetingById(meetingId)
                .orElseThrow(NotFoundMeetingException::new);

        return meeting.bindToMeetingDetails();
    }

    public List<MeetingDto> getMeetings(Long groupId) {
        return meetingQueryRepository.findMeetingsByGroupId(groupId);
    }
}
