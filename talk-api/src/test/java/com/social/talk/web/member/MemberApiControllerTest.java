package com.social.talk.web.member;

import com.social.talk.api.member.dto.MemberCreateDto;
import com.social.talk.api.member.service.MemberCommandService;
import com.social.talk.web.BaseApiConfig;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ExtendWith(MockitoExtension.class)
class MemberApiControllerTest extends BaseApiConfig {

    @MockBean
    private MemberCommandService memberCommandService;

    @DisplayName("회원가입 성공 테스트")
    @Test
    void signupSuccessTest() throws Exception {

        MemberCreateDto createDto = new MemberCreateDto("user1@email.com", "하하", "12121212", "12121212");

        mockMvc.perform(post(MEMBER_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createDto)))
                .andExpect(status().isCreated());

        assertThatNoException();
    }

    @DisplayName("회원가입 실패 테스트 - 비밀번호 길이 제한")
    @Test
    void signupFailTest() throws Exception {

        MemberCreateDto createDto = new MemberCreateDto("user1@email.com", "user", "1212", "1212");

        mockMvc.perform(post(MEMBER_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> {
                    result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class);
                });
    }

    @DisplayName("회원가입 실패 테스트 - 비밀번호 불일치")
    @Test
    void signupFailNotEqualsPasswordTest() throws Exception {

    }
}