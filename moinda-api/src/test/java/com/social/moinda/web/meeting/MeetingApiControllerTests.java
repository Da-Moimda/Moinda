package com.social.moinda.web.meeting;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.dto.MeetingJoinRequest;
import com.social.moinda.api.meeting.service.MeetingCommandService;
import com.social.moinda.api.meeting.service.MeetingQueryService;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meeting.entity.MeetingLocation;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.web.ApiURLProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.social.moinda.web.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
    void post_create_meeting() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        MeetingCreateDto meetingCreateDto = new MeetingCreateDto(1L, "스타벅스 센트럴점",
                "경기도 소향로 181", 4000, now);

        MeetingCreateResponse meetingCreateResponse =
                new MeetingCreateResponse(1L, new MeetingLocation("스타벅스 센트럴점", "경기도 소향로 181"),
                        4000, now);

        given(meetingCommandService.create(any(MeetingCreateDto.class)))
                .willReturn(meetingCreateResponse);

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEETING_API_URL)
                .content(toJson(meetingCreateDto))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("groupId").description("그룹 번호").attributes(field("constraint", "필수")),
                                        fieldWithPath("shopName").description("모임 장소명").attributes(field("constraint", "필수")),
                                        fieldWithPath("streetName").description("모임 장소 거리명").attributes(field("constraint", "필수")),
                                        fieldWithPath("amount").description("금액").attributes(field("constraint", "필수")),
                                        fieldWithPath("meetingDate").description("모임 날짜").attributes(field("constraint", "필수"))
                                ),
                                responseFields(
                                        fieldWithPath("meetingId").description("모임 번호"),
                                        fieldWithPath("meetingLocation.shopName").description("모임 장소명"),
                                        fieldWithPath("meetingLocation.streetName").description("모임 거리명"),
                                        fieldWithPath("amount").description("금액"),
                                        fieldWithPath("meetingDate").description("모임 날짜")
                                )
                        )
                );
    }

    @DisplayName("한 모임에 대한 상세조회에 성공한다.")
    @Test
    void get_meeting_details() throws Exception {

        Long memberId = 1L;

        List<MemberDto> memberDtoList = List.of(
                new MemberDto(1L, "user1@naver.com", "user1", "안녕하세요!!"),
                new MemberDto(2L, "user2@naver.com", "user2", "안녕하세요!!"),
                new MemberDto(3L, "user3@naver.com", "user3", "안녕하세요!!")
        );

        int userNum = memberDtoList.size();
        LocalDateTime meetingDate = LocalDateTime.of(2022, 7, 31, 2, 30);

        MeetingDetails meetingDetails = new MeetingDetails(memberDtoList, userNum, meetingDate);

        given(meetingQueryService.getMeetingDetails(anyLong()))
                .willReturn(meetingDetails);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.MEETING_API_URL + "/" + memberId + "/details"));

        perform.andExpect(status().isOk())
                .andExpect(content().json(toJson(meetingDetails)))
                .andDo(restDocs.document(
                                // TODO : to required PathParameter Description
                                responseFields(
                                        fieldWithPath("members[].memberId").description("모임 번호"),
                                        fieldWithPath("members[].email").description("모임 장소명"),
                                        fieldWithPath("members[].name").description("모임 거리명"),
                                        fieldWithPath("members[].introduce").description("금액"),
                                        fieldWithPath("userNum").description("참여자 수"),
                                        fieldWithPath("meetingDate").description("모임 날짜")
                                )
                        )
                );
    }

    @DisplayName("모임 참가에 성공한다.")
    @Test
    void post_join_meeting() throws Exception {
        Long meetingId = 1L;
        Long memberId = 1L;
        Long groupId = 1L;

        MeetingJoinRequest meetingJoinRequest = new MeetingJoinRequest(meetingId, memberId, groupId);

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEETING_API_URL + "/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(meetingJoinRequest)));

        perform.andExpect(status().isOk()).andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("meetingId").description("모임 번호").attributes(field("constraint", "필수")),
                                fieldWithPath("memberId").description("사용자 번호").attributes(field("constraint", "필수")),
                                fieldWithPath("groupId").description("그룹 번호").attributes(field("constraint", "필수"))
                        )
                )
        );
    }
    // TODO : 그룹 내 모임 전체 목록 보여주는 API 테스트 코드 필요.
}
