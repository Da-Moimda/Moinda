package com.social.moinda.web.group;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.service.GroupCommandService;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = GroupApiController.class)
public class GroupApiControllerTests extends BaseApiConfig {

    @MockBean
    private GroupCommandService groupCommandService;

    @DisplayName("그룹 만들기를 성공한다")
    @Test
    void createGroupSuccessTest() throws Exception {

        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "유저1", "안녕하세요?"
        ,"경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        perform.andExpect(status().is2xxSuccessful());
    }

    @DisplayName("사용자 번호가 Null 이여서 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfMemberIdTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(null, "그룹1", "안녕하세요?"
                ,"경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "사용자 번호가 필요합니다.");
    }

    @DisplayName("그룹명을 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfNameTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "", "안녕하세요?"
                ,"경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "그룹명을 입력해주세요.");
    }

    @DisplayName("소개글을 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfIntroduceTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", ""
                ,"경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "소개글을 입력해주세요.");

    }

    @DisplayName("주제를 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfConcernTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", "안녕하세요?"
                ,"경기도", "부천시", "", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "그룹 주제를 선택해주세요.");
    }

    @DisplayName("인원 수 조건에 맞지않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfCapacityTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", "안녕하세요?"
                ,"경기도", "부천시", "FREE", 5);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "최소 10명부터 가능합니다.");
    }

    private ResultActions getCreateResultActions(GroupCreateDto groupCreateDto) throws Exception {
        return mockMvc.perform(post(GROUP_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(groupCreateDto)));
    }

    private void failPerformDueToValidation(ResultActions perform, String expectedMessage) throws Exception {
        perform.andExpect(status().is4xxClientError())
                .andExpect(result -> {
                    result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class);
                    result.getResolvedException().getMessage().equals(expectedMessage);
                });
    }

}
