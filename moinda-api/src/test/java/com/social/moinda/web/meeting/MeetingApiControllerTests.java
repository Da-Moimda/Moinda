package com.social.moinda.web.meeting;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.service.MeetingCommandService;
import com.social.moinda.api.meeting.service.MeetingQueryService;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingApiController.class)
public class MeetingApiControllerTests extends BaseApiConfig {

    @MockBean
    private MeetingCommandService meetingCommandService;

    @MockBean
    private MeetingQueryService meetingQueryService;

    @DisplayName("모임 생성에 성공한다.")
    @Test
    void createMeetingSuccessTest() throws Exception {

        MeetingCreateDto meetingCreateDto = new MeetingCreateDto(1L, "스타벅스 센트럴점",
                "경기도 소향로 181", 4000, LocalDateTime.now());

        ResultActions perform = mockMvc.perform(post(MEETING_API_URL)
                .content(toJson(meetingCreateDto))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated());
    }

    @DisplayName("한 모임에 대한 상세조회에 성공한다.")
    @Test
    void getMeetingDetailsSuccessTest() throws Exception {

        Long memberId = 1L;

        List<MemberDto> memberDtoList = List.of(
                new MemberDto(1L, "user1@naver.com", "user1"),
                new MemberDto(2L, "user2@naver.com", "user2"),
                new MemberDto(3L, "user3@naver.com", "user3")
        );

        int userNum = memberDtoList.size();
        LocalDateTime meetingDate = LocalDateTime.of(2022, 7, 31, 2, 30);

        MeetingDetails meetingDetails = new MeetingDetails(memberDtoList, userNum, meetingDate);

        BDDMockito.given(meetingQueryService.getMeetingDetails(anyLong()))
                        .willReturn(meetingDetails);

        ResultActions perform = mockMvc.perform(get(MEETING_API_URL + "/" + memberId));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(meetingDetails)));
    }

}
