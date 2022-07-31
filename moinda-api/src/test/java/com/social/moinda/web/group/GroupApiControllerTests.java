package com.social.moinda.web.group;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.dto.GroupJoinRequest;
import com.social.moinda.api.group.service.GroupCommandService;
import com.social.moinda.api.group.service.GroupQueryService;
import com.social.moinda.core.domains.group.dto.GroupDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = GroupApiController.class)
public class GroupApiControllerTests extends BaseApiConfig {

    @MockBean
    private GroupCommandService groupCommandService;

    @MockBean
    private GroupQueryService groupQueryService;

    @DisplayName("그룹 만들기를 성공한다")
    @Test
    void successCreateGroupTest() throws Exception {

        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "유저1", "안녕하세요?"
                , "경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        perform.andExpect(status().isCreated());
    }

    @DisplayName("사용자 번호가 Null 이여서 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfMemberIdTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(null, "그룹1", "안녕하세요?"
                , "경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "사용자 번호가 필요합니다.");
    }

    @DisplayName("그룹명을 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfNameTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "", "안녕하세요?"
                , "경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "그룹명을 입력해주세요.");
    }

    @DisplayName("소개글을 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfIntroduceTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", ""
                , "경기도", "부천시", "FREE", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "소개글을 입력해주세요.");

    }

    @DisplayName("주제를 입력하지 않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfConcernTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", "안녕하세요?"
                , "경기도", "부천시", "", 30);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "그룹 주제를 선택해주세요.");
    }

    @DisplayName("인원 수 조건에 맞지않아 그룹 만들기를 실패한다.")
    @Test
    void failToCreateGroupBecauseOfCapacityTest() throws Exception {
        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", "안녕하세요?"
                , "경기도", "부천시", "FREE", 5);

        ResultActions perform = getCreateResultActions(groupCreateDto);

        failPerformDueToValidation(perform, "최소 10명부터 가능합니다.");
    }

    @DisplayName("전체 그룹 목록 조회에 성공한다.")
    @Test
    void successToGetGroupsTest() throws Exception {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryService.searchGroups()).willReturn(dtoList);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.GROUP_API_URL));

        perform.andExpect(status().is2xxSuccessful())
                .andExpect(content().json(toJson(dtoList)));
    }

    @DisplayName("특정한 그룹명 검색 및 조회를 성공한다.")
    @Test
    void successToGetGroupsOnSearchTest() throws Exception {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "부천그룹1", "부천시", "FREE", 20),
                new GroupDto(3L, "부천그룹3", "부천시", "FREE", 11)
        );

        given(groupQueryService.searchGroups(anyString())).willReturn(dtoList);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.GROUP_API_URL + "/" + "부천"));

        perform.andExpect(status().is2xxSuccessful())
                .andExpect(content().json(toJson(dtoList)));
    }

    @DisplayName("그룹 가입에 성공한다.")
    @Test
    void successJoinGroupTest() throws Exception {
        GroupJoinRequest joinRequest = new GroupJoinRequest(1L, 1L);

        doAnswer(answer -> {
            System.out.println("Stubbing ...");
            return null;
        }).when(groupCommandService).joinGroup(any(GroupJoinRequest.class));

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.GROUP_API_URL + "/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(joinRequest)));

        perform.andExpect(status().isOk());
    }

    private ResultActions getCreateResultActions(GroupCreateDto groupCreateDto) throws Exception {
        return mockMvc.perform(post(ApiURLProvider.GROUP_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(groupCreateDto)));
    }

    // TODO : 공통으로 뽑아보기
    private void failPerformDueToValidation(ResultActions perform, String expectedMessage) throws Exception {
        perform.andExpect(status().is4xxClientError())
                .andExpect(result -> {
                    result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class);
                    result.getResolvedException().getMessage().equals(expectedMessage);
                });
    }

}
