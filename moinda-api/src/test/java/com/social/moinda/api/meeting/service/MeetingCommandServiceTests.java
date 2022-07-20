package com.social.moinda.api.meeting.service;


import com.social.moinda.core.domains.meeting.Meeting;
import com.social.moinda.core.domains.meeting.MeetingLocation;
import com.social.moinda.core.domains.meeting.MeetingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MeetingCommandServiceTests {

    @Mock
    private MeetingRepository meetingRepository;

    @InjectMocks
    private MeetingCommandService meetingCommandService;

    @DisplayName("모임 생성하기 - 성공")
    @Test
    void successCreateMeetingTest() {
        Meeting meeting = new Meeting(null,
                new MeetingLocation("스타벅스 센트럴", "경기도 부천시 소향로 181"),
                4500,
                LocalDateTime.of(2022, 7, 31, 2, 30));

        given(meetingRepository.save(any(Meeting.class))).willReturn(meeting);

        meetingCommandService.create(meeting);

        then(meetingRepository).should(times(1)).save(meeting);
    }
}
