package com.social.moinda.api.meeting.service;

import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQueryService {

    private final MeetingQueryRepository meetingQueryRepository;

    public MeetingDetails getMeetingDetails(Long meetingId) {

        Meeting meeting = meetingQueryRepository.findMeeting(meetingId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 모임입니다."));

        System.out.println("meeting : " + meeting);

        return null;
    }
}
