package com.social.moinda.api.meeting.service;

import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        MeetingDetails meetingDetails = new MeetingDetails(null, 0, null);

        given(meetingQueryRepository.findMeetingById(anyLong()))
                .willReturn(Optional.of(meetingDetails));

        meetingQueryService.getMeetingDetails(meetingId);

        then(meetingQueryRepository).should(times(1)).findMeetingById(meetingId);
        assertThatNoException();
    }

    @DisplayName("해당 ID로 조회되지 않아서 모임 조회에 실패한다.")
    @Test
    void getMeetingDetailsFailTest() {

        Long memberId = 1L;

        given(meetingQueryRepository.findMeetingById(anyLong()))
                .willThrow(new NotFoundMeetingException());

        assertThatThrownBy(() -> meetingQueryService.getMeetingDetails(memberId))
                .isInstanceOf(NotFoundMeetingException.class)
                .hasMessageContaining("존재하지 않는 모임입니다.");
    }
}