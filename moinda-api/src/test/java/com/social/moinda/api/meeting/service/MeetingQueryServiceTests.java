package com.social.moinda.api.meeting.service;

import com.social.moinda.api.meeting.exception.NotFoundMeetingException;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
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

        Meeting meeting = new Meeting(null, null, 3000, null);
        given(meetingQueryRepository.findMeetingById(anyLong()))
                .willReturn(Optional.of(meeting));

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

    @DisplayName("특정한 그룹내의 모임 전체조회에 성공한다.")
    @Test
    void getMeetingsSuccessTest() {
        // 시작시간, 날짜, 위치, 가격
        List<MeetingDto> dtoList = List.of(
                new MeetingDto(1L,"스타벅스 부천점", 5000, LocalDateTime.now()),
                new MeetingDto(2L, "스타벅스 소사점", 4000, LocalDateTime.now()),
                new MeetingDto(3L, "스타벅스 상동점", 6000, LocalDateTime.now())
        );

        given(meetingQueryRepository.findMeetingsByGroupId(anyLong())).willReturn(dtoList);

        meetingQueryService.getMeetings(anyLong());

        then(meetingQueryRepository).should(times(1)).findMeetingsByGroupId(anyLong());
    }

}