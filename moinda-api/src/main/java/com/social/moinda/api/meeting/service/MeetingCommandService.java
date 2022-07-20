package com.social.moinda.api.meeting.service;

import com.social.moinda.core.domains.meeting.Meeting;
import com.social.moinda.core.domains.meeting.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingCommandService {

    private final MeetingRepository meetingRepository;

    public Meeting create(Meeting meeting) {

        Meeting entity = meetingRepository.save(meeting);

        return entity;
    }
}
