package com.social.moinda.web.member;

import com.social.moinda.api.member.dto.SignupRequest;
import com.social.moinda.api.member.service.MemberCommandService;
import com.social.moinda.api.member.service.MemberQueryService;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import com.social.moinda.core.domains.member.dto.SignupResponse;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.web.ApiURLProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Stream;

import static com.social.moinda.web.RestDocsConfig.field;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
    void post_signup() throws Exception {

        SignupRequest createDto = new SignupRequest("user1@email.com", "하하", "안녕하세요", "12121212", "12121212");

        SignupResponse signupResponse = new SignupResponse(1L, "user1@email.com", "하하", "안녕하세요", "12121212");

        given(memberCommandService.create(any(SignupRequest.class))).willReturn(signupResponse);

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createDto)));

        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(field("constraint", "이메일 형식")),
                                        fieldWithPath("name").description("이름").attributes(field("constraint", "빈칸 미허용")),
                                        fieldWithPath("introduce").description("자기소개").attributes(field("constraint", "빈칸 허용")),
                                        fieldWithPath("password").description("비밀번호").attributes(field("constraint", "8자리 이상")),
                                        fieldWithPath("confirmPassword").description("2차 비밀번호").attributes(field("constraint", "8자리 이상 & 위와 동일"))
                                ),
                                responseFields(
                                        fieldWithPath("memberId").description("사용자 번호"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("introduce").description("자기소개")
                                )
                        )
                );
        assertThatNoException();
    }

    @DisplayName("사용자와 가입한 그룹을 함께 조회하여 성공한다.")
    @Test
    void get_member_with_groupInfo() throws Exception {

        Member member = new Member("user1@email.com", "user1", "안녕하세요", "12121212");

        MemberDetails memberDetails = new MemberDetails(member.bindToMemberDto(), List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        ));

        given(memberQueryService.getMemberWithGroupInfo(anyLong())).willReturn(memberDetails);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.MEMBER_API_URL + "/" + 1L));

        perform.andExpect(status().isOk())
                .andExpect(content().json(toJson(memberDetails)))
                .andDo(restDocs.document(
                                responseFields(
                                        fieldWithPath("memberDto").description("사용자 정보내용"),
                                        fieldWithPath("memberDto.memberId").description("사용자 번호"),
                                        fieldWithPath("memberDto.email").description("이메일"),
                                        fieldWithPath("memberDto.name").description("이름"),
                                        fieldWithPath("memberDto.introduce").description("자기소개"),
                                        fieldWithPath("groups").description("가입한 그룹정보"),
                                        fieldWithPath("groups[].groupId").description("그룹 번호"),
                                        fieldWithPath("groups[].groupName").description("그룹명"),
                                        fieldWithPath("groups[].locationSi").description("그룹 활동 위치"),
                                        fieldWithPath("groups[].concern").description("그룹 주제"),
                                        fieldWithPath("groups[].userNum").description("인원 수")
                                )
                        )
                );
    }

    @Nested
    @DisplayName("회원가입을 할 때")
    class whileCreateSignup {

        @DisplayName("이메일 형식이 아니여서 실패한다.")
        @ParameterizedTest
        @ArgumentsSource(EmailSourceOutOfRange.class)
        void not_valid_type_email(String email) throws Exception {

            SignupRequest createDto = new SignupRequest(email, "하하", "안녕하세요", "12121212", "12121212");

            ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(createDto)));

            perform.andExpect(status().isBadRequest());
        }

        @DisplayName("이름을 작성하지 않아서 실패한다.")
        @ParameterizedTest
        @ArgumentsSource(UsernameSourceOutOfRange.class)
        void not_input_username(String username) throws Exception {

            SignupRequest createDto = new SignupRequest("user2@email.com", username, "안녕하세요", "12121212", "12121212");

            ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(createDto)));

            perform.andExpect(status().isBadRequest());
        }

        // TODO : 아직 조건을 만들지 않아서 실패하는 케이스.
        @DisplayName("1차, 2차 패스워드가 일치하지 않아 실패한다.")
        @ParameterizedTest
        @ArgumentsSource(PasswordSourceOutOfRange.class)
        void not_equals_password(String password, String confirmPassword) throws Exception {

            SignupRequest createDto = new SignupRequest("user2@email.com", "하하", "안녕하세요", password, confirmPassword);

            ResultActions perform = mockMvc.perform(post(ApiURLProvider.MEMBER_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(createDto)));

            perform.andExpect(status().isBadRequest());
        }
    }

    static class EmailSourceOutOfRange implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of((Object)null),
                    Arguments.of(""),
                    Arguments.of("\t"),
                    Arguments.of("\n"),
                    Arguments.of("user1")
            );
        }
    }

    static class UsernameSourceOutOfRange implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of((Object)null),
                    Arguments.of(""),
                    Arguments.of("\t"),
                    Arguments.of("\n")
            );
        }
    }

    static class PasswordSourceOutOfRange implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("12121212", "1212121212"),
                    Arguments.of("21212121", "277272727"),
                    Arguments.of("djskwkl2020", "3948dk2a")
            );
        }
    }

}