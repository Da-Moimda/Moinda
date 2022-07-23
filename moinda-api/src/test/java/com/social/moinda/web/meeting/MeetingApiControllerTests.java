package com.social.moinda.web.meeting;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.service.MeetingCommandService;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingApiController.class)
public class MeetingApiControllerTests extends BaseApiConfig {

    @MockBean
    private MeetingCommandService meetingCommandService;

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
}
