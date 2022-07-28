package com.social.moinda.api.meeting.service;

import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MeetingQueryServiceTests {

    @Mock
    private MeetingQueryRepository meetingQueryRepository;

    @InjectMocks
    private MeetingQueryService meetingQueryService;

    @DisplayName("모임에 대한 상세 정보 조회 성공")
    @Test
    void getMeetingDetailsSuccessTest() {

        Long meetingId = 1L;

        Meeting meeting = new Meeting(null, null, 0, null);

        given(meetingQueryRepository.findMeeting(anyLong())).willReturn(Optional.of(meeting));

        meetingQueryService.getMeetingDetails(meetingId);

        then(meetingQueryRepository).should(times(1)).findMeeting(meetingId);
        assertThatNoException();
    }
}