package com.social.moinda.web.member;

import com.social.moinda.api.member.dto.SignupRequest;
import com.social.moinda.api.member.service.MemberCommandService;
import com.social.moinda.api.member.service.MemberQueryService;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.web.ApiURLProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberApiController.class)
class MemberApiControllerTests extends BaseApiConfig {

    @MockBean
    private MemberCommandService memberCommandService;

    @MockBean
    private MemberQueryService memberQueryService;

    @DisplayName("회원가입 성공 테스트")
    @Test
    void signupSuccessTest() throws Exception {

        SignupRequest createDto = new SignupRequest("user1@email.com", "하하", "안녕하세요", "12121212", "12121212");

        mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createDto)))
                .andExpect(status().isCreated());

        assertThatNoException();
    }

    @DisplayName("회원가입 실패 테스트 - 비밀번호 길이 제한")
    @Test
    void signupFailTest() throws Exception {

        SignupRequest createDto = new SignupRequest("user1@email.com", "user", "안녕하세요", "1212", "1212");

        mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> {
                    result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class);
                });
    }

    @DisplayName("사용자와 가입한 그룹을 함께 조회하여 성공한다.")
    @Test
    void getMemberDetailsSuccessTest() throws Exception {

        Member member = new Member("user1@email.com", "user1", "안녕하세요", "12121212");

        MemberDetails memberDetails = new MemberDetails(member.bindToMemberDto(), List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        ));

        given(memberQueryService.getMemberWithGroupInfo(anyLong())).willReturn(memberDetails);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.MEMBER_API_URL + "/" + 1L));

        perform.andExpect(status().isOk())
                .andExpect(content().json(toJson(memberDetails)));
    }
}